package com.weblog.business.service;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.CommentInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.DeleteException;
import com.weblog.business.exception.ReviewException;

public interface BlogService {

    BlogInfo getBlogInfo(long bid);

    PostInfo[] getBlogAllPost(long bid, int page, int pageSize);

    CommentInfo[] getCommentInfo(long bid, long pid, boolean all, int page, int pageSize);

    void addCommentInfo(long bid, long pid, CommentInfo comment, long reply);
    void deleteCommentInfo(long bid, long pid, long cid) throws DeleteException;
    CommentInfo[] getAllReplyComment(long bid, long pid, long cid, int page, int pageSize);
    void reviewComment(long bid, long pid, long cid, boolean pass) throws ReviewException;


}
