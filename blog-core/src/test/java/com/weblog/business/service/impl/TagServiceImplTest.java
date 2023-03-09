package com.weblog.business.service.impl;

import com.weblog.business.entity.TagInfo;
import com.weblog.business.exception.EntityNotFoundException;
import com.weblog.business.service.TagService;
import com.weblog.persistence.mapper.TagMapper;
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
class TagServiceImplTest {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    TagService tagService;

    @SneakyThrows
    public TagInfo[] getPublicTags(int page, int pageSize) {
        return tagMapper.getPublicTags(page * pageSize, pageSize);
    }

    @SneakyThrows
    public TagInfo[] getBloggerTags(long bloggerId, int page, int pageSize) {
        return tagMapper.getBloggerTags(bloggerId, page * pageSize, pageSize);
    }

    @SneakyThrows
    public TagInfo[] getHotTags() {
        return tagMapper.getHotTags();
    }

    @Test
    @SneakyThrows
    void getTagInfo() {
        long existTid = 1L;
        long nonExistTid = 100000L;
        assertDoesNotThrow(()->tagService.getTagInfo(existTid));
        assertThrows(EntityNotFoundException.class, ()->tagService.getTagInfo(nonExistTid));
    }

    @Test
    @SneakyThrows
    void addTag() {
        TagInfo tag = new TagInfo(10L,"",null,"");
        assertEquals(10L, tagService.addTag(tag));
    }

    @Test
    @SneakyThrows
    void updateTag() {
        TagInfo existTag = new TagInfo(1L, "",null, "");
        TagInfo nonExistTag = new TagInfo(100000L, "",null, "");
        assertThrows(EntityNotFoundException.class, ()->tagService.updateTag(nonExistTag));
        assertDoesNotThrow(()->tagService.updateTag(existTag));
    }

    @Test
    @SneakyThrows
    public void deleteTag() {
        long existTid = 1L;
        long nonExistTid = 100000L;
        assertDoesNotThrow(()->tagService.deleteTag(existTid));
        assertThrows(EntityNotFoundException.class, ()->tagService.deleteTag(nonExistTid));
    }
}
