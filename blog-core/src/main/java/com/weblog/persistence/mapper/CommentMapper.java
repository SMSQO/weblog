package com.weblog.persistence.mapper;

import com.weblog.business.entity.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper {


    CommentInfo[] getCommentOnlyUnreviewed(long id, int start, int count);

    CommentInfo[] getComment(long pid, int start, int count);

    int addComment(long pid, long bid, String content, long replyTo);

    int deleteComment(long cid);


    CommentInfo[] getReplyComment(long replyTo);

    int reviewComment(long id, boolean pass);

    CommentInfo getCommentById(long id);
}
