package com.weblog.persistence.mapper;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.BloggerInfo;
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
class BloggerMapperTest {



    @Autowired
    private BloggerMapper bloggerMapper;


    @Test
    @Order(1)
    void getBloggerById() {
        val bloggerinfo = new BloggerInfo(1,"UmiKaiyo",null,"/file/blogger/1/avatar","15000000001","test@163.com","XX大学");
        assertEquals(bloggerinfo,bloggerMapper.getBloggerById(1));
    }

    @Test
    @Order(1)
    void getBloggerByContactAndPassword() {
        val bloggerinfo = bloggerMapper.getBloggerById(1);
        assertEquals(bloggerinfo,bloggerMapper.getBloggerByContactAndPassword("15000000001","password_1"));
    }

    @Test
    @Order(1)
    void updateBloggerInfo() {
        val bloggerinfo = bloggerMapper.getBloggerById(1);
        bloggerinfo.setName("WAhaha");
        bloggerMapper.updateBloggerInfo(bloggerinfo);
        assertEquals(bloggerinfo,bloggerMapper.getBloggerById(1));
    }
    @Test
    @Order(2)
    void addBloggerInfo() {
        val bloggerinfo = new BloggerInfo(3,"zy","123","/file/blogger/3/avatar","15333333333","test3@111.com","XX大学");
        bloggerMapper.addBloggerInfo(bloggerinfo);
        bloggerinfo.setPassword(null);
        assertEquals(bloggerinfo,bloggerMapper.getBloggerById(bloggerinfo.getId()));

    }

}