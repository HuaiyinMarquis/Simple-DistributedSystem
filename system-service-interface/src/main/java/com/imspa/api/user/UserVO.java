package com.imspa.api.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-12 16:59
 */
@Data
@Accessors(chain = true)
public class UserVO {
    private String id;
    private String name;
    private String passwordHash;
    private String roleId;
    private String roleName;
    private Date lastLoginTime;
    private String lastLoginClientIp;
    private Date createdTime;
    private String createdBy;
    private Date updatedTime;
    private String updatedBy;
}
