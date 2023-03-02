package com.weblog.persistence.mapper;

import com.weblog.business.entity.AttachmentInfo;
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
class AttachmentMapperTest {

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private BloggerMapper bloggerMapper;

    @Test
    @Order(1)
    void getBloggerAttachment() {
        val blogger = bloggerMapper.getBloggerById(1);
        assertArrayEquals(
                new AttachmentInfo[]{
                        new AttachmentInfo(1, "合金压缩包", "gz", "/file/blogger/1/", blogger, 1024),
                        new AttachmentInfo(2, "纳米压缩包", "gz", "/file/blogger/1/", blogger, 2048),
                },
                attachmentMapper.getBloggerAttachment(1, 0, 10)
        );
        assertArrayEquals(
                new AttachmentInfo[]{ new AttachmentInfo(1, "合金压缩包", "gz", "/file/blogger/1/", blogger, 1024)},
                attachmentMapper.getBloggerAttachment(1, 0, 1)
        );
        assertArrayEquals(
                new AttachmentInfo[]{ new AttachmentInfo(2, "纳米压缩包", "gz", "/file/blogger/1/", blogger, 2048), },
                attachmentMapper.getBloggerAttachment(1, 1, 1)
        );
    }

    @Test
    @Order(1)
    void getAttachmentInfo() {
        val blogger = bloggerMapper.getBloggerById(1);
        val attach = new AttachmentInfo(1, "合金压缩包", "gz", "/file/blogger/1/", blogger, 1024);
        assertEquals(attach, attachmentMapper.getAttachmentInfo(1));
    }

    @Test
    @Order(1)
    void getAttachmentMd5() {
        assertEquals("", attachmentMapper.getAttachmentMd5(2));
    }

    private AttachmentInfo newAttach() {
        val blogger = bloggerMapper.getBloggerById(1);
        val attach = new AttachmentInfo(0, "attach.png", "png", "/file/blogger/1/cafebabe", blogger, 20);
        attachmentMapper.addAttachmentInfo(attach, "cafebabe");
        return attach;
    }

    @Test
    @Order(2)
    void addAttachmentInfo() {
        val attach = newAttach();
        assertEquals(attach, attachmentMapper.getAttachmentInfo(attach.getId()));
    }

    @Test
    @Order(2)
    void deleteAttachmentInfo() {
        val attach = newAttach();
        attachmentMapper.deleteAttachmentInfo(attach.getId());
        assertNull(attachmentMapper.getAttachmentInfo(attach.getId()));
    }
}