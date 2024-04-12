package com.boogle.papplan.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DashBoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * endpoint : /api/dashboard/emp/8
     * Test ID  : T006-1
     */
    @Test
    @Transactional
    public void 대시보드데이터_정상() throws Exception {

        int empNo = 8;

        mockMvc.perform(get("/api/dashboard/emp/{empno}", empNo))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projects").exists())
                .andExpect(jsonPath("$.projects").isArray())
                .andExpect(jsonPath("$.projects").isNotEmpty())
                .andExpect(jsonPath("$.tasks").exists())
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks").isNotEmpty())
                .andExpect(jsonPath("$.prj_today").exists())
                .andExpect(jsonPath("$.prj_yesterday").exists())
                .andExpect(jsonPath("$.prj_week").exists())
                .andExpect(jsonPath("$.task_today").exists())
                .andExpect(jsonPath("$.task_yesterday").exists())
                .andExpect(jsonPath("$.task_week").exists())
        ;
    }

}