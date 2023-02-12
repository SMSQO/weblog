package com.weblog.business.service.impl;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.service.BloggerService;
import com.weblog.persistence.mapper.BloggerMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class BloggerServiceImpl implements BloggerService {

    @Autowired
    private BloggerMapper bloggerMapper;

    @Autowired
    private HttpServletRequest request;

    private final static String BLOGGER_KEY = "blogger";

    @Override
    public BloggerInfo getBloggerInfo(long uid) {
        return bloggerMapper.getBloggerById(uid);
    }

    @Override
    public BloggerInfo getSelfBloggerInfo() throws NotLoggedInException {
        val blogger = (BloggerInfo) request.getSession().getAttribute(BLOGGER_KEY);
        if (blogger == null) {
            throw new NotLoggedInException();
        }
        return blogger;
    }

    @Override
    public long getSelfBloggerId() throws NotLoggedInException {
        return getSelfBloggerInfo().getId();
    }

    @Override
    public void updateBloggerInfo(BloggerInfo info) throws EntityNotFoundException {
        final int ret = bloggerMapper.updateBloggerInfo(info);
        if (ret != 1) {
            throw new EntityNotFoundException(String.format("blogger with id %d not found", info.getId()));
        }
    }

    @Override
    public long addBloggerInfo(BloggerInfo info) throws LoginRegisterException {
        try {
            bloggerMapper.addBloggerInfo(info);
        } catch (DuplicateKeyException e) {
            throw new LoginRegisterException("Another blogger with same contact has been registered.");
        }
        return info.getId();
    }

    @Override
    public void loginBlogger(String contact, String password) throws LoginRegisterException {
        val blogger = bloggerMapper.getBloggerByContactAndPassword(contact, password);
        if (blogger == null) {
            throw new LoginRegisterException("Either contact or password wrong. Please retry.");
        }
        request.getSession().setAttribute(BLOGGER_KEY, blogger);
    }
}
