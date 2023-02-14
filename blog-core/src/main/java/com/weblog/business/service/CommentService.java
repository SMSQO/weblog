package com.weblog.business.service;

import com.weblog.business.entity.CommentInfo;
import com.weblog.business.exception.DeleteException;

public interface CommentService {
    CommentInfo[] getCommentInfo(long bid, long pid, boolean all, int page, int pageSize);

    void addCommentInfo(long bid, long pid, CommentInfo comment, long reply);

    void deleteCommentInfo(long bid, long pid, long cid) throws DeleteException;

    CommentInfo[] getAllReplyComment(long bid, long pid, long cid, int page, int pageSize);

    void reviewComment(long bid, long pid, long cid, boolean pass);
}
