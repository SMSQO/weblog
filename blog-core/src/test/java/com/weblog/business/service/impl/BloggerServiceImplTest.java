package com.weblog.business.service.impl;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.exception.LoginRegisterException;
import com.weblog.business.exception.SameContactException;
import com.weblog.business.service.AttachmentService;
import com.weblog.business.service.BloggerService;
import com.weblog.persistence.mapper.BloggerMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.weblog.business.ConstantUtil.AVATAR_REAL_PATH;
import static com.weblog.business.ConstantUtil.AVATAR_URL_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class BloggerServiceImplTest {

    @Autowired
    private BloggerService bloggerService;

    @Autowired
    private BloggerMapper bloggerMapper;

    @Test
    @SneakyThrows
    void getBloggerInfo() {
        long uid = 1;
//        val blogger = bloggerMapper.getBloggerById(uid);
//        if (blogger == null) {
//            throw new EntityNotFoundException(String.format("blogger with id %d not found", uid));
//        }
//        System.out.println(blogger);
        val expected = new BloggerInfo(
                1,"UmiKaiyo",null,"/file/blogger/1/avatar","15000000001","test@163.com","XX大学");
        assertEquals(expected, bloggerService.getBloggerInfo(uid));

        assertThrows(EntityNotFoundException.class, () ->
                bloggerService.getBloggerInfo(123));
    }

    @Test
    @SneakyThrows
    void updateBloggerInfo() {
        val duplicateContactInfo = new BloggerInfo(
                1,"new","1234","/file/blogger/1/avatar","15000000002","test@163.com","XX大学");
        val nonExistIdInfo = new BloggerInfo(
                1234,"new","1234","/file/blogger/1/avatar","12345","test@163.com","XX大学");

        assertThrows(SameContactException.class, () ->
                bloggerService.updateBloggerInfo(duplicateContactInfo));

        assertThrows(EntityNotFoundException.class, () ->
                bloggerService.updateBloggerInfo(nonExistIdInfo));
    }

    @Test
    @SneakyThrows
    void addBloggerInfo() {
        val newBlog = new BloggerInfo(
                0,"new","1234","/file/blogger/1/avatar","123","test@163.com","XX大学");
        assert(bloggerService.addBloggerInfo(newBlog)==3);
        val duplicate = new BloggerInfo(
                0,"new","1234","/file/blogger/1/avatar","123","test@163.com","XX大学");
        assertThrows(LoginRegisterException.class, () ->
                bloggerService.addBloggerInfo(duplicate));
    }

    private final static MultipartFile file =
            new MockMultipartFile(MediaType.ALL_VALUE, new byte[]{0, 1, 2, 3});

    @Test
    @SneakyThrows
    void updateBloggerAvatar() {
        val uid = 1;
        assertEquals(bloggerService.updateBloggerAvatar(uid, file), String.format(AVATAR_URL_PATTERN, uid));
    }
}
