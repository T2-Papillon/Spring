package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.*;
import com.boogle.papplan.service.task.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class ProjectProgressServiceImplTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = mock(TaskService.class); // TaskService의 Mock 객체 생성
        projectRepository = mock(ProjectRepository.class); // ProjectRepository의 Mock 객체 생성
        // ProjectServiceImpl 객체 초기화, projectRepository와 taskService를 인자로 전달
        projectService = new ProjectServiceImpl(projectRepository, null, null, taskService);
    }

    @Test
    @DisplayName("프로젝트 진행률 업데이트 테스트")
    void testUpdateProjectProgress() {
        // given
        Integer projNo = 1;
        // 테스트에 필요한 더미 TaskDTO 객체들을 생성하고 각각의 작업 진행률을 설정합
        TaskDTO task1 = new TaskDTO();
        task1.setTaskPercent(50);
        TaskDTO task2 = new TaskDTO();
        task2.setTaskPercent(75);

        List<TaskDTO> tasks = Arrays.asList(task1, task2);

        // 새 프로젝트 객체를 생성하고 프로젝트 번호를 설정
        Project project = new Project();
        project.setProjNo(projNo);
        // 생성한 프로젝트 객체를 Optional로 감싸서 생성 ( null 값 다루기 )
        Optional<Project> projectOptional = Optional.of(project);

        // Mock 설정: taskService가 주어진 프로젝트 번호에 해당하는 작업 목록을 반환하도록 설정
        when(taskService.getTasksByProjectId(projNo)).thenReturn(tasks);
        // Mock 설정: projectRepository가 주어진 프로젝트 번호로 프로젝트를 찾을 때 Optional<Project>를 반환하도록 설정
        when(projectRepository.findById(projNo)).thenReturn(projectOptional);

        // when
        projectService.updateProjectProgress(projNo);

        // then
        // 프로젝트 저장 메서드가 정확히 한 번 호출되었는지 확인
        Mockito.verify(projectRepository, times(1)).save(project);
        // 업데이트된 프로젝트 진행률이 예상대로인지 확인
        // 예상 진행률은 주어진 작업들의 평균 진행률인데, 여기서는 (50 + 75) / 2 = 62.5가 예상되며, 반올림하여 63이어야 합니다.
        assertEquals(63, project.getProjPercent());

//        // 가상의 프로젝트 번호
//        long projNo = 12345L; // long 타입으로 변경
//
//        // 가상의 작업 목록
//        List<TaskDTO> tasks = new ArrayList<>();
//        // 작업 추가
//        TaskDTO task1 = new TaskDTO();
//        task1.setTaskPercent(50); // 작업 진행률 설정
//        tasks.add(task1);
//        TaskDTO task2 = new TaskDTO();
//        task2.setTaskPercent(75); // 작업 진행률 설정
//        tasks.add(task2);
//
//        // 주어진 프로젝트 번호에 해당하는 작업 목록을 반환하도록 설정
//        when(taskService.getTasksByProjectId((int) projNo)).thenReturn(tasks);
//
//        // 프로젝트 진행률 업데이트 메서드 호출
//        projectService.updateProjectProgress((int) projNo);

    }
}

