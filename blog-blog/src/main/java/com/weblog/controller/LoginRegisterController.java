package com.weblog.controller;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.service.BloggerService;
import com.weblog.business.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginRegisterController {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private PermissionService permissionService;

    @PostMapping("/login")
    public long login(String contact, String password) throws LoginRegisterException {
        return permissionService.loginBlogger(contact, password);
    }

    @PostMapping("/logout")
    public void logout() throws LoginRegisterException {
        permissionService.logoutBlogger();
    }

    @PostMapping("/register")
    public long register(String name, String password, String contact, String email) throws LoginRegisterException {
        BloggerInfo blogger = new BloggerInfo();
        {
            blogger.setName(name);
            blogger.setPassword(password);
            blogger.setContact(contact);
            blogger.setEmail(email);
        }
        return bloggerService.addBloggerInfo(blogger);
    }
}
