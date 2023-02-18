package com.weblog.business.service.impl;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.SameContactException;
import com.weblog.business.service.BloggerService;
import com.weblog.persistence.mapper.BloggerMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class BloggerServiceImpl implements BloggerService {

    @Autowired
    private BloggerMapper bloggerMapper;

    @Autowired
    private HttpServletRequest request;

    private final static String BLOGGER_KEY = PermissionServiceImpl.BLOGGER_KEY;

    @Override
    public BloggerInfo getBloggerInfo(long uid) {
        return bloggerMapper.getBloggerById(uid);
    }

    @Override
    public void updateBloggerInfo(BloggerInfo info) throws EntityNotFoundException, SameContactException {
        final int ret;
        try {
            ret = bloggerMapper.updateBloggerInfo(info);
        } catch (DataIntegrityViolationException e) {
            throw new SameContactException(String.format("Someone else has the same concat: %s", info.getContact()));
        }
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
    public long loginBlogger(String contact, String password) throws LoginRegisterException {
        val blogger = bloggerMapper.getBloggerByContactAndPassword(contact, password);
        if (blogger == null) {
            throw new LoginRegisterException("Either contact or password wrong. Please retry.");
        }
        val bloggerId = blogger.getId();
        request.getSession().setAttribute(BLOGGER_KEY, bloggerId);
        return bloggerId;
    }

    @Override
    public void logoutBlogger() throws LoginRegisterException {
        if (request.getSession().getAttribute(BLOGGER_KEY) == null) {
            throw new LoginRegisterException("Not logged in");
        }
        request.getSession().removeAttribute(BLOGGER_KEY);
    }
}
