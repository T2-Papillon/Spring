package com.boogle.papplan.service.dashboard;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.service.project.ProjectService;
import com.boogle.papplan.service.task.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest

public class DashBoardServiceTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void beforeEmployee() {
        
    }

    @Test
    @Transactional
    @DisplayName("대시보드 데이터 집계 및 필터링")
    public void 대시보드_집계_필터링() {

    }


}
