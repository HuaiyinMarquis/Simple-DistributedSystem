package com.imspa.web.auth;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-12 15:09
 */
public enum Permissions {
    ALL("/all", "所有权限"),
    ROLE_GET("/role/get/**", "权限获取"),
    USER("/user", "用户列表"),
    USER_GET("/user/get", "用户查询"),
    RESOURCE("/resource", "资源获取"),
    ORDER_GET("/order/get/**","订单查询");

    private String url;
    private String desc;

    Permissions(String url, String desc) {
        this.url = url;
        this.desc = desc;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDesc() {
        return this.desc;
    }
}
