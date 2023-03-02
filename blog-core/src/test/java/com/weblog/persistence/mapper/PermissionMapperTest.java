package com.weblog.persistence.mapper;

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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql("/schema.sql")
@Sql("/data.sql")
class PermissionMapperTest {

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    void checkTagOwner() {
        assertTrue(permissionMapper.checkTagOwner(1, 7));
        assertFalse(permissionMapper.checkTagOwner(1, 9));
        assertFalse(permissionMapper.checkTagOwner(1, 1));
    }

    @Test
    void checkAttachmentOwner() {
        assertTrue(permissionMapper.checkAttachmentOwner(1, 1));
        assertFalse(permissionMapper.checkAttachmentOwner(1, 3));
    }

    @Test
    void checkPostAuthor() {
        assertTrue(permissionMapper.checkPostAuthor(1, 1));
        assertFalse(permissionMapper.checkPostAuthor(1, 6));
    }

    @Test
    void checkPostPublic() {
        assertTrue(permissionMapper.checkPostPublic(3));
        assertFalse(permissionMapper.checkPostPublic(1));
    }
}