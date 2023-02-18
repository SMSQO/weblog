package com.weblog.business.service;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.SameContactException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface BloggerService {

    BloggerInfo getBloggerInfo(long uid) throws EntityNotFoundException;

    void updateBloggerInfo(BloggerInfo bloggerinfo) throws EntityNotFoundException, SameContactException;

    long addBloggerInfo(BloggerInfo info) throws LoginRegisterException;

    String updateBloggerAvatar(long uid, MultipartFile file) throws IOException;
}
