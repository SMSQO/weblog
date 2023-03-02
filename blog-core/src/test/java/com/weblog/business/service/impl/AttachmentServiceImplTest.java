package com.weblog.business.service.impl;

import com.weblog.business.entity.AttachmentInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.AttachmentService;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.weblog.business.ConstantUtil.ATTACHMENT_REAL_PATH;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class AttachmentServiceImplTest {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private BloggerMapper bloggerMapper;

    @Test
    void getBloggerAttachments() {
        val attaches = attachmentService.getBloggerAttachments(1, 0, 10);
        val blogger = bloggerMapper.getBloggerById(1);

        val expect = new AttachmentInfo[]{
                new AttachmentInfo(1, "合金压缩包", "gz", "/file/blogger/1/attachment/1", blogger, 1024),
                new AttachmentInfo(2, "纳米压缩包", "gz", "/file/blogger/1/attachment/2", blogger, 2048),
        };
        assertArrayEquals(expect, attaches);
    }

    @Test
    @SneakyThrows
    void getAttachmentInfo() {
        val blogger = bloggerMapper.getBloggerById(1);
        val expected = new AttachmentInfo(
                1, "合金压缩包", "gz",
                "/file/blogger/1/attachment/1",
                blogger, 1024);
        assertEquals(expected, attachmentService.getAttachmentInfo(1));

        assertThrows(EntityNotFoundException.class, () ->
                attachmentService.getAttachmentInfo(65536));
    }

    private final static MultipartFile file =
            new MockMultipartFile(MediaType.ALL_VALUE, new byte[]{0, 1, 2, 3});

    private String getFileMD5(MultipartFile file) throws IOException {
        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException ignore) {
        }
        return ""; // Never happen
    }

    @SneakyThrows
    private long addAttachment() {
        return attachmentService.saveAttachment(1, "mock.ts", file);
    }

    @Test
    @SneakyThrows
    void saveAttachment() {
        val actualId = addAttachment();
        val md5 = getFileMD5(file);
        val expected = new AttachmentInfo(
                actualId,
                "mock.ts",
                "ts",
                String.format("/file/blogger/1/attachment/%d", actualId),
                bloggerMapper.getBloggerById(1), 4
        );
        assertEquals(expected, attachmentService.getAttachmentInfo(actualId));
        assertTrue(new File(ATTACHMENT_REAL_PATH, md5).exists());
    }

    @Test
    @SneakyThrows
    void deleteAttachment() {
        val actualId = addAttachment();
        attachmentService.deleteAttachment(actualId);
        assertThrows(EntityNotFoundException.class, () ->
                attachmentService.getAttachmentInfo(actualId));
        // 逻辑上被删除的文件, 实际上不会被删除. 下面的语句没有必要:
        // assertFalse(new File(ATTACHMENT_REAL_PATH, getFileMD5(file)).exists());
    }

    @Test
    @SneakyThrows
    void getAttachmentMd5sum() {
        val expected = "";
        assertEquals(expected, attachmentService.getAttachmentMd5sum(1));
        assertThrows(EntityNotFoundException.class, () ->
                attachmentService.getAttachmentMd5sum(65536));
    }
}