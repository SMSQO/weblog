package com.weblog.controller;

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
class BloggerControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    public BloggerControllerTest(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac).build();
    }

    @SneakyThrows
    private MockHttpSession getNewLoggedInSession() {
        val sess = new MockHttpSession();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/login")
                .param("contact", "15000000001")
                .param("password", "password_1")
                .session(sess)
        );
        return sess;
    }

    @Test
    void getSelfBloggerInfo() throws Exception {
        val sess = getNewLoggedInSession();
        val req = MockMvcRequestBuilders
                .get("/blogger/self")
                .session(sess);

        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0"),
                        jsonPath("$.content.name").value("UmiKaiyo"),
                        jsonPath("$.content.email").value("test@163.com"),
                        jsonPath("$.content.contact").value("15000000001")

                );
    }

    @Test
    void getBloggerInfo() throws Exception {
        val req = MockMvcRequestBuilders
                .get("/blogger/1")
                .session(getNewLoggedInSession());


        mockMvc.perform(req)
                .andDo(it -> log.info(it.getResponse().getContentAsString(StandardCharsets.UTF_8))) // optional
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.code").value("0"),
                        jsonPath("$.content.name").value("UmiKaiyo"),
                        jsonPath("$.content.email").value("test@163.com"),
                        jsonPath("$.content.contact").value("15000000001")
                );
    }

}