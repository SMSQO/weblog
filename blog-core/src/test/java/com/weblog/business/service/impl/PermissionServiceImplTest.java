package com.weblog.business.service.impl;

import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.NotLoggedInException;
import com.weblog.business.exception.PermissionDeniedException;
import com.weblog.business.service.PermissionService;
import com.weblog.persistence.mapper.BloggerMapper;
import com.weblog.persistence.mapper.PermissionMapper;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Permission;
import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class PermissionServiceImplTest {

    public final static String BLOGGER_KEY = "blogger-id";

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private BloggerMapper bloggerMapper;

    @Autowired
    private PermissionService permissionService;

    @Test
    @SneakyThrows
    void loginBlogger() {
        String contact = "15000000001";
        String correctPwd = "password_1";
        String wrongPwd = "000";
        assertEquals(permissionService.loginBlogger(contact, correctPwd),1);
        assertThrows(LoginRegisterException.class, ()->permissionService.loginBlogger(contact, wrongPwd));
    }

    @Test
    @SneakyThrows
    void logoutBlogger() {
        assertThrows(LoginRegisterException.class, ()->permissionService.logoutBlogger());
    }

    @Test
    @SneakyThrows
    void getSelfBloggerIdTest() {
        assertThrows(NotLoggedInException.class, ()->permissionService.getSelfBloggerId());
        request.getSession().setAttribute(BLOGGER_KEY, 1L);
        assertEquals(permissionService.getSelfBloggerId(),1L);
    }
    @SneakyThrows
    long getSelfBloggerId() {
        return permissionService.getSelfBloggerId();
    }

    @Test
    @SneakyThrows
    void assertIsSelfBlogger() {
        request.getSession().setAttribute(BLOGGER_KEY, 1L);
        assertThrows(PermissionDeniedException.class, ()->permissionService.assertIsSelfBlogger(2L));
    }

    @Test
    @SneakyThrows
    public void assertIsSelfTag(){
        request.getSession().setAttribute(BLOGGER_KEY, 1L);
        assertThrows(PermissionDeniedException.class, ()->permissionService.assertIsSelfTag(10L));

    }

    @Test
    @SneakyThrows
    void assertIsSelfAttachment() {
        request.getSession().setAttribute(BLOGGER_KEY, 1L);
        assertThrows(PermissionDeniedException.class, ()->permissionService.assertIsSelfAttachment(10L));

    }

    @Test
    @SneakyThrows
    void assertIsSelfPost() {

        request.getSession().setAttribute(BLOGGER_KEY, 1L);
        assertThrows(PermissionDeniedException.class, ()->permissionService.assertIsSelfPost(10L));

    }

    @Test
    @SneakyThrows
    void assertIsPostAvailable() {
        assertThrows(PermissionDeniedException.class, ()->permissionService.assertIsPostAvailable(10L));

    }
}
