package com.weblog.persistence.mapper;

import com.weblog.business.entity.TagInfo;
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

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class TagMapperTest {


    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BloggerMapper bloggerMapper;

    @Test
    @Order(1)
    void getTagInfoByName() {
        val javaTag = new TagInfo(1, "java", null, "desc-java");
        assertEquals(javaTag, tagMapper.getTagInfoByName("java"));
        assertNull(tagMapper.getTagInfoByName("not existed"));
    }

    @Test
    @Order(1)
    void getPublicTags() {
        val publicTags = new TagInfo[]{
                new TagInfo(1, "java", null, "desc-java"),
                new TagInfo(2, "C", null, "desc-C"),
                new TagInfo(3, "C++", null, "desc-Cpp"),
                new TagInfo(4, "Rust", null, "desc-Rust"),
                new TagInfo(5, "Kotlin", null, "desc-kotlin"),
                new TagInfo(6, "Haskell", null, "desc-haskell"),
        };

        assertArrayEquals(publicTags, tagMapper.getPublicTags(0, 10));

        val head3 = new TagInfo[]{publicTags[0], publicTags[1], publicTags[2]};
        assertArrayEquals(head3, tagMapper.getPublicTags(0, 3));

        val publicFrom1Count3 = new TagInfo[]{publicTags[1], publicTags[2], publicTags[3]};
        assertArrayEquals(publicFrom1Count3, tagMapper.getPublicTags(1, 3));
    }

    @Test
    @Order(1)
    void getBloggerTags() {
        val blogger1 = bloggerMapper.getBloggerById(1);
        TagInfo[] blogger1Tags = {
                new TagInfo(7, "C#", blogger1, "C#-desc"),
                new TagInfo(8, "Go", blogger1, "Go-desc"),
        };
        assertArrayEquals(blogger1Tags, tagMapper.getBloggerTags(1, 0, 10));
        assertArrayEquals(new TagInfo[]{blogger1Tags[0]}, tagMapper.getBloggerTags(1, 0, 1));
        assertArrayEquals(new TagInfo[]{blogger1Tags[1]}, tagMapper.getBloggerTags(1, 1, 1));
    }

    @Test
    @Order(1)
    void getHotTags() {
        val hotTags = new TagInfo[]{
                new TagInfo(2, "C", null, "desc-C"),
                new TagInfo(1, "java", null, "desc-java"),
                new TagInfo(3, "C++", null, "desc-Cpp"),
                new TagInfo(4, "Rust", null, "desc-Rust"),
                new TagInfo(5, "Kotlin", null, "desc-kotlin"),
                new TagInfo(6, "Haskell", null, "desc-haskell"),
        };
        assertArrayEquals(hotTags, tagMapper.getHotTags());
    }

    @Test
    @Order(1)
    void getTagInfo() {
        assertEquals(
                new TagInfo(7, "C#", bloggerMapper.getBloggerById(1), "C#-desc"),
                tagMapper.getTagInfo(7)
        );
        assertNull(tagMapper.getTagInfo(65536));
    }

    @Test
    @Order(2)
    void addTag() {
        val newTag = new TagInfo(0, "OCaml", bloggerMapper.getBloggerById(1), "OCaml-desc");
        tagMapper.addTag(newTag);
        assertEquals(newTag, tagMapper.getTagInfo(newTag.getId()));
    }

    @Test
    @Order(2)
    void updateTag() {
        addTag();
        val target = tagMapper.getTagInfoByName("Ocaml");
        target.setDescription("Ocaml-new-description");
        tagMapper.updateTag(target);
        assertEquals(target, tagMapper.getTagInfoByName("Ocaml"));
    }

    @Test
    @Order(2)
    void deleteTag() {
        addTag();
        val target = tagMapper.getTagInfoByName("Ocaml");
        tagMapper.deleteTag(target.getId());
        assertNull(tagMapper.getTagInfo(target.getId()));
    }
}