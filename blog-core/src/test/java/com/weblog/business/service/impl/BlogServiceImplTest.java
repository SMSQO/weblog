package com.weblog.business.service.impl;

import com.weblog.business.entity.BlogInfo;
import com.weblog.business.entity.PostInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.BlogService;
import com.weblog.persistence.mapper.BlogMapper;
import com.weblog.persistence.mapper.PostMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static com.weblog.business.ConstantUtil.BLOGS_URL_PATTERN;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class BlogServiceImplTest{
    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private PostMapper postMapper;

    @Test
    @SneakyThrows
    void getBlogInfo(){
        val nonExistBid = 10000000;
        val selfWatch = false;
        assertThrows(EntityNotFoundException.class, ()->blogService.getBlogInfo(nonExistBid,selfWatch));
    }

    @Test
    @SneakyThrows
    void getBlogAllPost() {
        val selfWatch = true;
        val bid = 1;
        val page = 1;
        val pageSize = 1;
        val ret = blogService.getBlogAllPost(selfWatch, bid, page, pageSize);
        assertNotNull(ret);
    }
}
