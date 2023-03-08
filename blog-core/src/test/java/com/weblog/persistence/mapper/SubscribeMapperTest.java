package com.weblog.persistence.mapper;

import com.weblog.business.entity.BloggerInfo;
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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class SubscribeMapperTest {

    @Autowired
    private SubscribeMapper subscribeMapper;


    @Test
    void subscribe() {
        subscribeMapper.subscribe(1,2);
        assertEquals(true,subscribeMapper.subscribed(1,2));
    }

    @Test
    void unsubscribe() {
        subscribeMapper.unsubscribe(2,1);
        assertEquals(false,subscribeMapper.subscribed(2,1));

    }

    @Test
    void subscribed() {
        assertEquals(true,subscribeMapper.subscribed(2,1));

    }

    @Test
    void bothBloggerExists() {
        assertEquals(true,subscribeMapper.bothBloggerExists(2,1));
        assertEquals(false,subscribeMapper.bothBloggerExists(4,1));
        assertEquals(false,subscribeMapper.bothBloggerExists(4,5));


    }


    @Test
    void getOnesFansCount(){
        assertEquals(1,subscribeMapper.getOnesFansCount(2));
    }



    @Test
    void getOnesFans() {
        assertArrayEquals(new BloggerInfo[]{new BloggerInfo(1,"UmiKaiyo",null,"/file/blogger/1/avatar","15000000001","test@163.com","XX大学")},subscribeMapper.getOnesFans(2));
    }
}