package com.weblog.business.service.impl;

import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.SubscribeService;
import com.weblog.persistence.mapper.SubscribeMapper;
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

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class SubscribeServiceImplTest {

    @Autowired
    private SubscribeMapper subscribeMapper;
    @Autowired
    private SubscribeService subscribeService;

    @Test
    @SneakyThrows
    void subscribe() {
        long publisher = 100L;
        long fan = 200L;

        assertThrows(EntityNotFoundException.class, ()->subscribeService.subscribe(publisher,fan));
    }

    @Test
    @SneakyThrows
    public void unsubscribe() {
        long nonExistPublisher = 100L;
        long nonExistFan = 200L;
        assertThrows(EntityNotFoundException.class, ()->subscribeService.subscribe(nonExistPublisher,nonExistFan));

        long publisher = 1L;
        long fan = 2L;
        assertDoesNotThrow(()->subscribeService.subscribe(publisher,fan));
    }

    @SneakyThrows
    public boolean subscribed(long publisher, long fan) {
        return subscribeMapper.subscribed(publisher, fan);
    }
}
