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
class BlogMapperTest {

    @Autowired
    private BlogMapper blogMapper;


    @Test
    @Order(1)
    void getBlogByIdAll() {
        val bloginfo = new BlogInfo(null,0,0,0,10,null);
        assertEquals(bloginfo,blogMapper.getBlogByIdAll(1));

    }

    @Test
    @Order(1)
    void getBlogByIdPublic() {
        val bloginfo = new BlogInfo(null,0,0,0,6,null);
        assertEquals(bloginfo,blogMapper.getBlogByIdPublic(1));
    }

    @Test
    @Order(1)
    void updateBlogVisitCount() {
        val blog = blogMapper.getBlogByIdAll(1);
        blogMapper.updateBlogVisitCount(1,5);
        assertEquals(blog,blogMapper.getBlogByIdAll(1));
    }

}