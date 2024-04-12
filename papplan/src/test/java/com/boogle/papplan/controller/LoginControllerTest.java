package com.boogle.papplan.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * endpoint : /api/signin
     * Test ID : T001-01
     */
    @Test
    @Transactional
    public void 로그인_정상() throws Exception {

        String id = "hr1@boogle.com";
        String password = "hrpass1";

        String requestBody = " { " +
                " \"email\": \"" + id + "\", " +
                " \"password\": \"" + password + "\" } ";

        mockMvc.perform(post("/api/signin")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eno").exists())
                .andExpect(jsonPath("$.eno").value(1))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.email").value("hr1@boogle.com"))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").value("김지원"))
                .andExpect(jsonPath("$.dept_no").exists())
                .andExpect(jsonPath("$.dept_no").value("HR"))
                .andExpect(jsonPath("$.position_id").exists())
                .andExpect(jsonPath("$.position_id").value("TEAM_LEADER"))
                .andDo(print());
    }

    /**
     * endpoint : /api/signin
     * Test ID : T001-02
     */
    @Test
    @Transactional
    public void 로그인_비정상() throws Exception {

        String id = "hr1@boogle.com";
        String password = "hrpass";

        String requestBody = " { " +
                " \"email\": \"" + id + "\", " +
                " \"password\": \"" + password + "\" } ";

        mockMvc.perform(post("/api/signin")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Login failed"))
                .andDo(print());
    }
}