package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.Task;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
public class ProjectServiceImplTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectService projectService;

    /* 라희 */

    @Autowired
    private TaskService taskService;


    private String email = "finance2@boogle.com";

    @Test
    @DisplayName("로그인한 사용자가 참여한 프로젝트 검색 - 프로젝트명 기준")
    void searchByProjTitleForLoggedInUserProject() {

        // given
        String searchTerm = "장바구니 결제 로직 변경 요청 건";

        // when
        List<ProjectDTO> foundProjects = projectService.searchProjects(searchTerm, 0, 10);

        // then
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");
        Assertions.assertTrue(foundProjects.stream().anyMatch(project -> project.getProjTitle().contains(searchTerm)),
                "검색된 프로젝트 중 하나 이상은 검색어를 포함해야 합니다.");
    }

    @Test
    @DisplayName("로그인한 사용자가 참여한 프로젝트 검색 - PM 기준")
    void searchByProjPmForLoggedInUserProject() {

        // given
        String searchTerm = "서현우";

        // when
        List<ProjectDTO> foundProjects = projectService.searchProjects(searchTerm, 0, 10);

        // then
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");
        Assertions.assertTrue(foundProjects.stream().anyMatch(project -> project.getProjPm().equals(searchTerm)),
                "검색된 프로젝트 중 하나 이상은 검색어와 일치하는 PM을 가져야 합니다.");
    }

    @Test
    @DisplayName("로그인한 사용자가 참여한 프로젝트 검색 - Contributor 기준")
    void searchByContributorForLoggedInUserProject() {

        // given
        String searchTerm = "정윤서";

        // when
        List<ProjectDTO> foundProjects = projectService.searchProjects(searchTerm, 0, 10);

        // then
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");
        boolean found = foundProjects.stream()
                .flatMap(projectDTO -> projectDTO.getContributors().stream())
                .anyMatch(contributorDTO -> contributorDTO.getName().equals(searchTerm));
        Assertions.assertTrue(found, "검색된 프로젝트 중 적어도 하나는 검색어와 일치하는 Contributor를 포함해야 합니다.");
    }


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
//        // given
//        Integer projNo = 1;
//        // 테스트에 필요한 더미 TaskDTO 객체들을 생성하고 각각의 작업 진행률을 설정합
//        TaskDTO task1 = new TaskDTO();
//        task1.setTaskPercent(50);
//        TaskDTO task2 = new TaskDTO();
//        task2.setTaskPercent(75);
//
//        List<TaskDTO> tasks = Arrays.asList(task1, task2);
//
//        // 새 프로젝트 객체를 생성하고 프로젝트 번호를 설정
//        Project project = new Project();
//        project.setProjNo(projNo);
//        // 생성한 프로젝트 객체를 Optional로 감싸서 생성 ( null 값 다루기 )
//        Optional<Project> projectOptional = Optional.of(project);
//
//        // Mock 설정: taskService가 주어진 프로젝트 번호에 해당하는 작업 목록을 반환하도록 설정
//        when(taskService.getTasksByProjectId(projNo)).thenReturn(tasks);
//        // Mock 설정: projectRepository가 주어진 프로젝트 번호로 프로젝트를 찾을 때 Optional<Project>를 반환하도록 설정
//        when(projectRepository.findById(projNo)).thenReturn(projectOptional);
//
//        // when
//        projectService.updateProjectProgress(projNo);
//
//        // then
//        // 프로젝트 저장 메서드가 정확히 한 번 호출되었는지 확인
//        Mockito.verify(projectRepository, times(1)).save(project);
//        // 업데이트된 프로젝트 진행률이 예상대로인지 확인
//        // 예상 진행률은 주어진 작업들의 평균 진행률인데, 여기서는 (50 + 75) / 2 = 62.5가 예상되며, 반올림하여 63이어야 합니다.
//        assertEquals(63, project.getProjPercent());

        // 가상의 프로젝트 번호
        long projNo = 12345L; // long 타입으로 변경

        // 가상의 작업 목록
        List<TaskDTO> tasks = new ArrayList<>();
        // 작업 추가
        TaskDTO task1 = new TaskDTO();
        task1.setTaskPercent(50); // 작업 진행률 설정
        tasks.add(task1);
        TaskDTO task2 = new TaskDTO();
        task2.setTaskPercent(75); // 작업 진행률 설정
        tasks.add(task2);

        // 주어진 프로젝트 번호에 해당하는 작업 목록을 반환하도록 설정
        when(taskService.getTasksByProjectId((int) projNo)).thenReturn(tasks);

        // 프로젝트 진행률 업데이트 메서드 호출
        projectService.updateProjectProgress((int) projNo);

    }
}

