package com.weblog.business.service.impl;

import com.weblog.business.entity.CommentInfo;
import com.weblog.business.service.CommentService;
import com.weblog.persistence.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentInfo[] getCommentInfo(long bid, long pid, boolean all, int page, int pageSize) {
        CommentInfo[] commentInfos;
        if (!all) {
            commentInfos = commentMapper.getCommentOnlyUnreviewed(pid, page * pageSize, pageSize);
        } else {
            commentInfos = commentMapper.getComment(pid, page * pageSize, pageSize);
        }
        return commentInfos;
    }

    @Override
    public void addCommentInfo(long bid, long pid, CommentInfo comment, long reply) {
        commentMapper.addComment(pid, bid, comment.getContent(), reply);
    }

    @Override
    public void deleteCommentInfo(long bid, long pid, long cid) {
        commentMapper.deleteComment(cid);
    }

    @Override
    public CommentInfo[] getAllReplyComment(long bid, long pid, long cid, int page, int pageSize) {
        return commentMapper.getReplyComment(cid);
    }

    @Override
    public void reviewComment(long bid, long pid, long cid, boolean pass) {
        commentMapper.reviewComment(cid, pass);
    }
}
