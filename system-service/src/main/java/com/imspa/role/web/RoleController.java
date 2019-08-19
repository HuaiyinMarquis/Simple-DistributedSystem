package com.imspa.role.web;

import com.imspa.role.service.RoleService;
import com.imspa.web.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-13 13:32
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/get/{id}")
    public String get(@PathVariable("id") String id, @RequestAttribute("userInfo")User user) {
        return roleService.get(id).toString();
    }
}
