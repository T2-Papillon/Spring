package com.boogle.papplan.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * /api/project
     * Test ID : T003-1
     */
    @Test
    @Transactional
    public void 프로젝트_전체조회() throws Exception {

        mockMvc.perform(get("/api/project")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$",hasSize(greaterThan(0))))
                //.andDo(print());

    }

    /**
     * /api/project/status/{status}
     * Test ID : T003-2
     */
    @Test
    @Transactional
    public void 프로젝트_조회_유효상태값() throws Exception {

        String status = "DONE";

        mockMvc.perform(get("/api/project/status/{status}", status)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$",hasSize(greaterThan(0))))
                //.andDo(print());

    }

    /**
     * /api/project
     * Test ID : T003-3
     */
    @Test
    @Transactional
    public void 프로젝트_상태조회_이상상태값() throws Exception {

        String status = "DON";

        mockMvc.perform(get("/api/project/status/{status}", status)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
                //.andDo(print());
    }
}