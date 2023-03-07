package com.weblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.entity.PostInfo;
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
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@Sql("/schema.sql")
@Sql("/data.sql")
@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@WebAppConfiguration
class PostControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public PostControllerTest(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac).build();
    }

    @SneakyThrows
    private MockHttpSession getNewLoggedInSession() {
        val sess = new MockHttpSession();
        sess.setAttribute("blogger-id", 1l);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                .param("contact", "15000000001")
                .param("password", "password_1")
                .session(sess)
        );
        return sess;
    }

    @Test
    void addPost() throws Exception {
        val blog = new BloggerInfo(1, "UmiKaiyo", null, "", "15000000001", "test@test.com", "XX大学");
        val per = new PostInfo.PostPermission(true, true);
        val pos = new PostInfo(8, "理塘", "理塘简介", "到达世界最高层——理塘。诶呀，这不是丁真嘛", blog, null, "", "", per, 0, 0, 1);
        val posStr = mapper.writeValueAsString(pos);
        log.info(posStr);
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .post("/blog/1/post")
                .session(sess)
                .content(posStr)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );

    }


    @Test
    void getBloggerPosts() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .get("/blog/1/post")
                .session(sess)
                .param("page", "0")
                .param("perpage", "10");
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );
    }


    @Test
    void getPostInfo() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .get("/blog/1/post/2")
                .session(sess);
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );
    }

    @Test
    void getPostDetail() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .get("/blog/1/post/2/detail")
                .session(sess);
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );
    }


    @Test
    void reviewComment() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .post("/blog/1/post/3/comment/1/review")
                .session(sess)
                .param("pass", "true");
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );
    }

    @Test
    void updatePostInfo() throws Exception {
        val pos = "到达世界最高层——理塘。诶呀，这不是丁真嘛";
        val posStr = mapper.writeValueAsString(pos);
        log.info(posStr);
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .patch("/blog/1/post/1/detail")
                .session(sess)
                .content(posStr)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );

    }

    @Test
    void updatePostDetail() throws Exception {
        val blog = new BloggerInfo(1, "UmiKaiyo", null, "", "15000000001", "test@test.com", "XX大学");
        val per = new PostInfo.PostPermission(true, true);
        val pos = new PostInfo(8, "理塘", "理塘简介", "到达世界最高层——理塘。诶呀，这不是丁真嘛", blog, null, "", "", per, 0, 0, 1);
        val posStr = mapper.writeValueAsString(pos);
        log.info(posStr);
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .patch("/blog/1/post/1")
                .session(sess)
                .content(posStr)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );

    }


    @Test
    void deletePost() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .delete("/blog/1/post/3")
                .session(sess);
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );
    }

}