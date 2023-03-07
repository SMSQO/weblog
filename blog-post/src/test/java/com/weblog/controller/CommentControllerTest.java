package com.weblog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weblog.business.entity.BloggerInfo;
import com.weblog.business.entity.CommentInfo;
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
class CommentControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public CommentControllerTest(WebApplicationContext wac) {
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
    void getCommentInfo() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .get("/blog/1/post/3/comment")
                .session(sess)
                .param("all", "false")
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
    void addCommentInfo() throws Exception {
        val blog = new BloggerInfo(1, "UmiKaiyo", null, "", "15000000001", "test@test.com", "XX大学");
        val comment = new CommentInfo(1, blog, "1！5！LTC！", null, null, null, true);
        val commentStr = mapper.writeValueAsString(comment);
        log.info(commentStr);
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .post("/blog/1/post/3/comment")
                .session(sess)
                .content(commentStr)
                .contentType(MediaType.APPLICATION_JSON)
                .param("reply", "1");
        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0")
                );
    }

    @Test
    void deleteCommentInfo() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .delete("/blog/1/post/3/comment/1")
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
    void getAllReplyComment() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .get("/blog/1/post/3/comment/1/replied")
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


}