package com.boogle.papplan.service.search;

import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.*;
import com.boogle.papplan.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class SearchServiceImplTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectPriorityRepository projectPriorityRepository;

    @Autowired
    private ProjectStatusRepository projectStatusRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SearchService searchService;

    @Test
    @DisplayName("전체 프로젝트에서 프로젝트명으로 프로젝트 검색")
    void searchByProjTitleInAll() {

        // given
        String searchTerm = "장바구니 결제 로직 변경 요청 건";

        // when
        List<ProjectDTO> foundProjects = searchService.searchProjects(searchTerm, 0, 10);

        // then
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");
        Assertions.assertTrue(foundProjects.stream().anyMatch(project -> project.getProjTitle().contains(searchTerm)),
                "검색 결과에는 '" + searchTerm + "'이(가) 포함된 프로젝트가 있어야 합니다.");
    }

    @Test
    @DisplayName("전체 프로젝트에서 PM으로 프로젝트 검색")
    public void searchByProjPMInAll() {

        // given
        String pmName = "서현우";

        // when
        List<ProjectDTO> foundProjects = searchService.searchProjects(pmName, 0, 10);

        // then
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");
        Assertions.assertTrue(foundProjects.stream().anyMatch(project -> project.getProjPm().equals(pmName)),
                "검색 결과에는 PM이 '" + pmName + "'인 프로젝트가 있어야 합니다.");
    }

    @Test
    @DisplayName("전체 프로젝트에서 참여자로 프로젝트 검색")
    public void searchByContributorInAll() {

        // given
        String contributorName = "정윤서";

        // when
        List<ProjectDTO> foundProjects = searchService.searchProjects(contributorName, 0, 10);


        // then
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");
        boolean hasContributor = foundProjects.stream()
                .flatMap(projectDTO -> projectDTO.getContributors().stream())
                .anyMatch(contributor -> contributor.getName().equals(contributorName));
        Assertions.assertTrue(hasContributor, "검색된 프로젝트들 중 최소 하나는 '" + contributorName + "'를 Contributor로 포함해야 합니다.");
    }
  
    @Test
    @DisplayName("유효한 projectStatusId로 검색하면 필터링된 프로젝트를 반환")
    void searchProjectsByValidStatus() {
        // given
        String validProjectStatusId = "DOING";

        // when
        List<ProjectDTO> foundProjects = searchService.findProjectsByStatusIdDto(validProjectStatusId);

        // then
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");
        boolean allMatch = foundProjects.stream()
                .allMatch(projectDTO -> projectDTO.getProjectStatus().equals(validProjectStatusId));
        Assertions.assertTrue(allMatch, "검색 결과에는 해당 상태의 프로젝트만 반환되어야 합니다.");
    }

    @Test
    @DisplayName("projectStatusId 없이 검색하면 예외 발생")
    void searchProjectsByNullStatus() {
        // given
        String projectStatusId = null;

        // when, then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            searchService.findProjectsByStatusIdDto(projectStatusId);
        });
    }

    @Test
    @DisplayName("유효하지 않은 projectStatusId로 검색했을 경우 예외 발생")
    void searchProjectsByInvalidStatus() {
        // given
        String invalidProjectStatusId = "ING";

        // when, then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            searchService.findProjectsByStatusIdDto(invalidProjectStatusId);
        });
    }
}
