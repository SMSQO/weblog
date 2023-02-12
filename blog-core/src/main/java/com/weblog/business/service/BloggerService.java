package com.weblog.business.service;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.NotLoggedInException;

public interface BloggerService {

    BloggerInfo getBloggerInfo(long uid);

    BloggerInfo getSelfBloggerInfo() throws NotLoggedInException;

    long getSelfBloggerId() throws NotLoggedInException;

    void updateBloggerInfo(BloggerInfo bloggerinfo) throws EntityNotFoundException;

    long addBloggerInfo(BloggerInfo info) throws LoginRegisterException;

    void loginBlogger(String contact, String password) throws LoginRegisterException;
}
