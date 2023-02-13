package com.weblog.business.service;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.SameContactException;

public interface BloggerService {

    BloggerInfo getBloggerInfo(long uid);

    void updateBloggerInfo(BloggerInfo bloggerinfo) throws EntityNotFoundException, SameContactException;

    long addBloggerInfo(BloggerInfo info) throws LoginRegisterException;

    long loginBlogger(String contact, String password) throws LoginRegisterException;

    void logoutBlogger() throws LoginRegisterException;
}
