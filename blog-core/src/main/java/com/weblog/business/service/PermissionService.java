package com.weblog.business.service;

import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.exception.PermissionDeniedException;

public interface PermissionService {

    long loginBlogger(String contact, String password) throws LoginRegisterException;

    void logoutBlogger() throws LoginRegisterException;

    long getSelfBloggerId() throws NotLoggedInException;

    void assertIsSelfBlogger(long uid) throws NotLoggedInException, PermissionDeniedException;

    void assertIsSelfTag(long tid) throws NotLoggedInException, PermissionDeniedException;

    void assertIsSelfAttachment(long aid) throws NotLoggedInException, PermissionDeniedException;

    void assertIsSelfPost(long pid) throws NotLoggedInException, PermissionDeniedException;

    // 检查当前用户是否有权限查看此博文. 有些博文被设置为仅自己可见, 就需要用到这个.
    void assertIsPostAvailable(long pid) throws NotLoggedInException, PermissionDeniedException;
}
