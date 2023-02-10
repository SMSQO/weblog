package com.weblog.business.service.impl;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.CommentInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.DeleteException;
import com.weblog.business.exception.ReviewException;
import com.weblog.business.service.BlogService;
import org.springframework.stereotype.Service;

// TODO
@Service
public class BlogServiceImpl implements BlogService {

    @Override
    public BlogInfo getBlogInfo(long bid) {
        return null;
    }

    @Override
    public PostInfo[] getBlogAllPost(long bid, int page, int pageSize) {
        return new PostInfo[0];
    }

    @Override
    public CommentInfo[] getCommentInfo(long bid, long pid, boolean all, int page, int pageSize) {
        return new CommentInfo[0];
    }

    @Override
    public void addCommentInfo(long bid, long pid, CommentInfo comment, long reply) {

    }

    @Override
    public void deleteCommentInfo(long bid, long pid, long cid) throws DeleteException {

    }

    @Override
    public CommentInfo[] getAllReplyComment(long bid, long pid, long cid, int page, int pageSize) {
        return new CommentInfo[0];
    }

    @Override
    public void reviewComment(long bid, long pid, long cid, boolean pass) throws ReviewException {

    }
}
