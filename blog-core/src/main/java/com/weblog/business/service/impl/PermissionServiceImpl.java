package com.weblog.business.service.impl;

import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.exception.PermissionDeniedException;
import com.weblog.business.service.PermissionService;
import com.weblog.persistence.mapper.BloggerMapper;
import com.weblog.persistence.mapper.PermissionMapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class PermissionServiceImpl implements PermissionService {

    public final static String BLOGGER_KEY = "blogger-id";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private BloggerMapper bloggerMapper;

    @Override
    public BloggerInfo getSelfBloggerInfo() throws NotLoggedInException {
        val bloggerId = getSelfBloggerId();
        return bloggerMapper.getBloggerById(bloggerId);
    }

    @Override
    public long getSelfBloggerId() throws NotLoggedInException {
        val bloggerId = request.getSession().getAttribute(BLOGGER_KEY);
        if (bloggerId == null) {
            throw new NotLoggedInException();
        }
        return (long) bloggerId;
    }

    @Override
    public void assertIsSelfBlogger(long uid) throws NotLoggedInException, PermissionDeniedException {
        if (getSelfBloggerId() != uid) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public void assertIsSelfTag(long tid) throws NotLoggedInException, PermissionDeniedException {
        if (!permissionMapper.checkTagOwner(getSelfBloggerId(), tid)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public void assertIsSelfAttachment(long aid) throws NotLoggedInException, PermissionDeniedException {
        if (!permissionMapper.checkAttachmentOwner(getSelfBloggerId(), aid)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public void assertIsSelfPost(long pid) throws NotLoggedInException, PermissionDeniedException {
        if (!permissionMapper.checkPostAuthor(getSelfBloggerId(), pid)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public void assertIsPostAvailable(long pid) throws NotLoggedInException, PermissionDeniedException {
        val uid = getSelfBloggerId();
        if (!permissionMapper.checkPostAuthor(uid, pid) && !permissionMapper.checkPostPublic(pid)) {
            throw new PermissionDeniedException();
        }
    }
}
