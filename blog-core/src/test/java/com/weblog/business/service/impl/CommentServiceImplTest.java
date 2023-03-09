package com.weblog.business.service.impl;

import com.weblog.business.entity.CommentInfo;
import com.weblog.business.service.CommentService;
import com.weblog.persistence.mapper.CommentMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class CommentServiceImplTest {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentService commentService;

    @Test
    @SneakyThrows
    void getCommentInfo() {
        long bid = 1L;
        long pid = 1L;
        boolean all = false;
        int page = 1;
        int pageSize = 1;
        assertArrayEquals(commentService.getCommentInfo(bid, pid, all, page, pageSize),commentMapper.getCommentOnlyUnreviewed(pid, page * pageSize, pageSize));
        all = true;
        assertArrayEquals(commentService.getCommentInfo(bid, pid, all, page, pageSize),commentMapper.getComment(pid, page * pageSize, pageSize));

    }

    @SneakyThrows
    public void addCommentInfo() {
        long bid = 1L;
        long pid = 4L;
        CommentInfo comment = new CommentInfo();
        long reply = 1L;
        commentService.addCommentInfo(pid, bid, comment, reply);
    }


    @SneakyThrows
    void deleteCommentInfo() {
        commentService.deleteCommentInfo(0L,0L,1L);
    }

    @Test
    @SneakyThrows
    void getAllReplyComment() {
        assertNotNull(commentService.getAllReplyComment(0L,0L,3L,0,0));
    }

    @SneakyThrows
    public void reviewComment() {
        commentService.reviewComment(0L,0L,3L, true);
    }
}
