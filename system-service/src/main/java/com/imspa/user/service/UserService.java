package com.imspa.user.service;

import com.google.common.base.Strings;
import com.imspa.api.user.SearchUserRequest;
import com.imspa.api.user.SearchUserResponse;
import com.imspa.api.user.UserVO;
import com.imspa.role.service.RoleService;
import com.imspa.user.mapper.UserMapper;
import com.imspa.user.model.UserExample;
import com.imspa.web.Exception.UnauthorizedException;
import com.imspa.web.pojo.User;
import com.imspa.web.util.RedisConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-12 12:16
 */
@Service("userService")
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;

    @Resource(name = "redisTemplate")
    private HashOperations<String, byte[], byte[]> hashOperations;
    @Autowired
    private HashMapper<Object, byte[], byte[]> hashMapper;


    public void update(UserVO userVO) {
        User user = new User();
        user.setId(userVO.getId());
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(userVO.getName()).andRoleIdEqualTo(userVO.getRoleId())
                .andRoleNameEqualTo(userVO.getRoleName()).andUpdatedTimeEqualTo(new Date());

        userMapper.updateByExampleSelective(user, example);
    }

    public SearchUserResponse search(SearchUserRequest request) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        if (!Strings.isNullOrEmpty(request.getName()))
            criteria.andNameEqualTo(request.getName());
        RowBounds rowBounds = new RowBounds((request.getPageIndex() - 1) * request.getPageSize(), request.getPageSize());

        long totalRecord = userMapper.countByExample(example);
        List<User> users = userMapper.selectByExampleWithRowbounds(example, rowBounds);

        SearchUserResponse response = new SearchUserResponse();
        response.setUsers(users.stream().map(user -> new UserVO()
                .setId(user.getId()).setName(user.getName()).setRoleId(user.getRoleId())
                .setRoleName(user.getRoleName()).setCreatedBy(user.getCreatedBy())
                .setCreatedTime(user.getCreatedTime()).setLastLoginClientIp(user.getLastLoginClientIp()))
                .collect(Collectors.toList()));
        response.setTotalRecord((int) totalRecord);
        response.setTotalPage((int) Math.ceil(response.getTotalRecord() / (double) request.getPageSize()));
        return response;
    }

    public void create(UserVO userVO) throws Exception {
        User user = new User();
        user.setName(userVO.getName());
        user.setPasswordHash(userVO.getPasswordHash()); //TODO
        if (!validateRole(userVO.getRoleId(), userVO.getRoleName()))
            throw new Exception("illegal Request Exception");
        user.setRoleId(userVO.getRoleId());
        user.setRoleName(userVO.getRoleName());
        user.setCreatedTime(new Date());
        user.setCreatedBy("admin");
    }

    public User login(String userName, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(userName);

        User user = userMapper.selectByExample(example).get(0);
        if (null == user)
            throw new UnauthorizedException("user name not exist");

        if (!StringUtils.equals(password, user.getPasswordHash()))
            throw new UnauthorizedException("user name or password wrong");

        roleService.get(user.getRoleId()); //for role cache

        hashOperations.putAll(RedisConstant.USER_SESSION_INFO_ + user.getName(), hashMapper.toHash(user));
        hashOperations.getOperations().expire(RedisConstant.USER_SESSION_INFO_ + user.getName(), 30, TimeUnit.MINUTES);

        return user;
    }

    private Boolean validateRole(String roleId, String roleName) {
        //TODO
        return Boolean.TRUE;
    }

}
