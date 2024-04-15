package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.ProjectRepository;
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
public class ProjectSearchServiceImplTest {

    @Autowired
    private ProjectService projectService;

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

    @Test
    @DisplayName("상태별로 프로젝트 검색")
    void searchByProjectStatus() {
        // given
        String status = "DOING";

        // when
        List<ProjectDTO> foundProjects = projectService.getProjectsByStatus(status);

        // then
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");
        Assertions.assertTrue(foundProjects.stream().allMatch(project -> project.getProjectStatus().equals(status)),
                "검색된 프로젝트는 모두 해당 상태여야 합니다.");
    }
}

