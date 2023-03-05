package com.weblog.persistence.mapper;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.entity.CommentInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BloggerMapper bloggerMapper;


    @Test
    @Order(1)
    void getCommentOnlyUnreviewed() {
        val blogger1 = bloggerMapper.getBloggerById(1);
        val blogger3 = bloggerMapper.getBloggerById(3);

        assertArrayEquals(        new CommentInfo[]{new CommentInfo(4,blogger1,"理塘丁真","/blog/1/post/1/detail","/blog/1/post/1/comment/4/replied",null,false)}
                ,commentMapper.getCommentOnlyUnreviewed(1,0,1));





    }

    @Test
    @Order(1)
    void getComment() {
        val blogger1 = bloggerMapper.getBloggerById(1);


        val commentInfo =                         new CommentInfo(1,blogger1,"dsads","/blog/1/post/3/detail","/blog/1/post/3/comment/1/replied",null,false);

        assertEquals(commentInfo,commentMapper.getCommentById(1));
    }


//    private AttachmentInfo newAttach() {
//        val blogger = bloggerMapper.getBloggerById(1);
//        val attach = new AttachmentInfo(0, "attach.png", "png", "/file/blogger/1/cafebabe", blogger, 20);
//        attachmentMapper.addAttachmentInfo(attach, "cafebabe");
//        return attach;
//    }
    @Test
    @Order(2)
    void addComment() {
        val blogger3 = bloggerMapper.getBloggerById(3);


        val commentInfo = new CommentInfo(7,blogger3,"nihao","/blog/1/post/1/detail","/blog/1/post/1/comment/7/replied",commentMapper.getCommentById(1),false);

        commentMapper.addComment(1,3,"nihao",1);
        assertEquals(commentInfo,commentMapper.getCommentById(7));



    }

    @Test
    @Order(2)
    void deleteAttachmentInfo() {

        commentMapper.deleteComment(1);
        assertNull(commentMapper.getCommentById(1));
    }

    @Test
    @Order(1)
    void getReplyComment() {
        val blogger1 = bloggerMapper.getBloggerById(1);


        assertArrayEquals(        new CommentInfo[]{new CommentInfo(2,null,"1!5!","/blog/1/post/1/detail","/blog/1/post/1/comment/2/replied",commentMapper.getCommentById(1),false)}
,commentMapper.getReplyComment(1));

    }

    @Test
    @Order(2)
    void reviewComment() {
        val blogger1 = bloggerMapper.getBloggerById(1);

        CommentInfo commentInfo = new CommentInfo(1,blogger1,"dsads","/blog/1/post/3/detail","/blog/1/post/3/comment/1/replied",null,true);

        commentMapper.reviewComment(1,true);

        assertEquals(commentInfo,commentMapper.getCommentById(1));

    }

    @Test
    @Order(1)
    void getCommentById() {
        val blogger1 = bloggerMapper.getBloggerById(1);

        CommentInfo commentInfo = new CommentInfo(1,blogger1,"dsads","/blog/1/post/3/detail","/blog/1/post/3/comment/1/replied",null,false);

        assertEquals(commentInfo,commentMapper.getCommentById(1));

    }


}