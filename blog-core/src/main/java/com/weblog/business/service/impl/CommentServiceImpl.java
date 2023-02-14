package com.weblog.business.service.impl;

import com.weblog.business.entity.CommentInfo;
import com.weblog.business.exception.DeleteException;
import com.weblog.business.service.CommentService;
import com.weblog.persistence.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public CommentInfo[] getCommentInfo(long bid, long pid, boolean all, int page, int pageSize) {
        CommentInfo[] commentInfos;
        if (!all) {
            commentInfos = commentMapper.getCommentOnlyUnreviewed(pid);
        } else {
            commentInfos = commentMapper.getComment(pid);
        }
        ArrayList<CommentInfo> comments = new ArrayList<>();
        if (commentInfos != null) {
            for (int i = 0; i < commentInfos.length; i++) {
                if (i >= page * pageSize && i < (page + 1) * pageSize) {
                    commentInfos[i].setPost(String.format("/blog/%d/post/%d", bid, pid));
                    comments.add(commentInfos[i]);
                }
            }
        }
        commentInfos = new CommentInfo[comments.size()];
        if (comments.size() > 0)
            comments.toArray(commentInfos);
        return commentInfos;
    }

    @Override
    public void addCommentInfo(long bid, long pid, CommentInfo comment, long reply) {
        commentMapper.addComment(pid, bid, comment.getContent(), reply);
    }

    @Override
    public void deleteCommentInfo(long bid, long pid, long cid) throws DeleteException {
        commentMapper.deleteComment(cid);
    }

    @Override
    public CommentInfo[] getAllReplyComment(long bid, long pid, long cid, int page, int pageSize) {
        CommentInfo[] commentInfos = commentMapper.getReplyComment(cid);
        ArrayList<CommentInfo> comments = new ArrayList<>();
        if (commentInfos != null) {
            for (int i = 0; i < commentInfos.length; i++) {
                if (i >= page * pageSize && i < (page + 1) * pageSize) {
                    commentInfos[i].setPost(String.format("/blog/%d/post/%d", bid, pid));
                    comments.add(commentInfos[i]);
                }
            }
        }
        commentInfos = new CommentInfo[comments.size()];
        if (comments.size() > 0)
            comments.toArray(commentInfos);
        return commentInfos;
    }

    @Override
    public void reviewComment(long bid, long pid, long cid, boolean pass) {
        commentMapper.reviewComment(cid, pass);
    }
}
