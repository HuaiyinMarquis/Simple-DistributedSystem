package com.imspa.role.service;

import com.imspa.role.mapper.RoleMapper;
import com.imspa.web.pojo.Role;
import com.imspa.web.util.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-12 14:13
 */
@Service
public class RoleService {
    @Resource(name = "redisTemplate")
    private HashOperations<String, byte[], byte[]> hashOperations;

    @Autowired
    private HashMapper<Object, byte[], byte[]> hashMapper;

    @Autowired
    private RoleMapper roleMapper;

    public Role get(String id) {
        Map<byte[], byte[]> entries = hashOperations.entries(RedisConstant.ROLE_PERMISSION_MAPPING_ + id);
        if (entries.size() != 0)
            return (Role) hashMapper.fromHash(entries);

        Role role = roleMapper.selectByPrimaryKey(id);
        if (role != null)
            hashOperations.putAll(RedisConstant.ROLE_PERMISSION_MAPPING_ + id, hashMapper.toHash(role));
        return role;
    }

    public List<Role> getAll() {
        return null;
    }

    public void update(Role role) {

    }
}
