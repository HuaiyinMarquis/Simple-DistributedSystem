package com.imspa.web.auth;

import com.imspa.web.Exception.ForbiddenException;
import com.imspa.web.Exception.UnauthorizedException;
import com.imspa.web.pojo.Role;
import com.imspa.web.pojo.User;
import com.imspa.web.util.RedisConstant;
import com.imspa.web.util.WebConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-16 14:39
 */
public class SecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);
    private AntPathMatcher matcher = new AntPathMatcher();
    private HashOperations<String, byte[], byte[]> hashOperations;
    private HashMapper<Object, byte[], byte[]> hashMapper;

    public SecurityFilter(HashOperations<String, byte[], byte[]> hashOperations, HashMapper<Object, byte[], byte[]> hashMapper) {
        this.hashOperations = hashOperations;
        this.hashMapper = hashMapper;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Optional<String> optional = PermissionUtil.getAllPermissionUrlItem().stream()
                .filter(permissionItem -> matcher.match(permissionItem, request.getRequestURI())).findFirst();
        if (!optional.isPresent()) { //TODO some api not config permission will direct do
            chain.doFilter(servletRequest, servletResponse);
            return;
        }

        try {
            validateAuthentication(request, optional.get());
            flushSessionAndToken(((User) request.getAttribute("userInfo")), response);
            chain.doFilter(servletRequest, servletResponse);
        } catch (ForbiddenException e) {
            logger.debug("occur forbidden exception:{}", e.getMessage());
            response.setStatus(403);
            ServletOutputStream output = response.getOutputStream();
            output.print(e.getMessage());
            output.flush();
        } catch (UnauthorizedException e) {
            logger.debug("occur unauthorized exception:{}", e.getMessage());
            response.setStatus(401);
            ServletOutputStream output = response.getOutputStream();
            output.print(e.getMessage());
            output.flush();
        }
    }

    @Override
    public void destroy() {

    }

    private void validateAuthentication(HttpServletRequest request, String permission) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authHeader))
            throw new UnauthorizedException("no auth header");

        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(WebConstant.WEB_SECRET)
                    .parseClaimsJws(authHeader.replace("Bearer ", ""))
                    .getBody();
        } catch (Exception e) {
            throw new UnauthorizedException(e.getMessage());
        }

        String userName = (String) claims.get("user");
        String roleId = (String) claims.get("role");

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(roleId))
            throw new UnauthorizedException("token error,user:" + userName);

        if (new Date().getTime() > claims.getExpiration().getTime())
            throw new UnauthorizedException("token expired,user:" + userName);


        User user = (User) hashMapper.fromHash(hashOperations.entries(RedisConstant.USER_SESSION_INFO_ + userName));
        if (user == null)
            throw new UnauthorizedException("session expired,user:" + userName);


        if (validateRolePermission(permission, user))
            request.setAttribute("userInfo", user);
    }

    private Boolean validateRolePermission(String permission, User user) {
        Role role = (Role) hashMapper.fromHash(hashOperations.entries(RedisConstant.ROLE_PERMISSION_MAPPING_ + user.getRoleId()));
        if (role.getPermissions().contains(Permissions.ALL.getUrl()))
            return Boolean.TRUE;

        if (role.getPermissions().contains(permission))
            return Boolean.TRUE;

        throw new ForbiddenException("do not have permission for this request");
    }

    private void flushSessionAndToken(User user, HttpServletResponse response) {
        hashOperations.getOperations().expire(RedisConstant.USER_SESSION_INFO_ + user.getName(), 30, TimeUnit.MINUTES);

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("user", user.getName());
        claimsMap.put("role", user.getRoleId());
        response.setHeader("Authorization",TokenAuthenticationService.getAuthenticationToken(claimsMap));
    }

}
