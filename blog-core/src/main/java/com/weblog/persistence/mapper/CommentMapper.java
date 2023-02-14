package com.weblog.persistence.mapper;

import com.weblog.business.entity.CommentInfo;
import com.weblog.business.exception.DeleteException;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper {


    @Nullable
    CommentInfo[] getCommentOnlyUnreviewed(long id);

    @Nullable
    CommentInfo[] getComment(long id);

    int addComment(long pid, long bid, String content, long replyTo);

    int deleteComment(long cid) throws DeleteException;


    @Nullable
    CommentInfo[] getReplyComment(long replyTo);

    int reviewComment(long id, boolean pass);
}
