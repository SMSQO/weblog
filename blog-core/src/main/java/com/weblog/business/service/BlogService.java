package com.weblog.business.service;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.CommentInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.DeleteException;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.ReviewException;

import javax.servlet.http.HttpServletRequest;

public interface BlogService {

    BlogInfo getBlogInfo(long bid, long uid, HttpServletRequest request) throws EntityNotFoundException;

    PostInfo[] getBlogAllPost(long uid, long bid, int page, int pageSize);

    CommentInfo[] getCommentInfo(long bid, long pid, boolean all, int page, int pageSize);

    void addCommentInfo(long bid, long pid, CommentInfo comment, long reply);
    void deleteCommentInfo(long bid, long pid, long cid) throws DeleteException;
    CommentInfo[] getAllReplyComment(long bid, long pid, long cid, int page, int pageSize);
    void reviewComment(long bid, long pid, long cid, boolean pass) throws ReviewException;


}
