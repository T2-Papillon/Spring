package com.boogle.papplan.service.search;

import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.*;
import com.boogle.papplan.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
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
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private SearchService searchService;

    @BeforeEach
    void beforeSearch() {

        // ProjectPriority 생성 및 저장
        ProjectPriority projectPriority = new ProjectPriority();
        projectPriority.setProjectPriorityId("LV0");
        projectPriority.setProjectPriorityName("긴급");
        projectPriorityRepository.save(projectPriority);

        // ProjectStatus 생성 및 저장
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setProjectStatusId("TODO");
        projectStatus.setProjectStatusName("진행예정");
        projectStatusRepository.save(projectStatus);

        // Department 생성 및 저장
        Department department = new Department();
        department.setDept_no("FIN"); // 실제 부서 코드
        department.setDept_name("재무팀"); // 실제 부서명
        departmentRepository.save(department);

        // Position 생성 및 저장
        Position position = new Position();
        position.setPosition_id("STAFF"); // 실제 직책 코드
        position.setPosition_name("사원"); // 실제 직책명
        positionRepository.save(position);

        // Employees 객체 생성 및 설정
        Employees employees = new Employees();
        employees.setName("홍길동");
        employees.setEmail("finance5@boogle.com");
        employees.setPassword("finpass5");
        employees.setDepartment(department);
        employees.setPosition(position);
        employeeRepository.save(employees);

        // Project 설정
        Project project = new Project();
        project.setProjTitle("장바구니 결제 로직 변경 요청 건");
        project.setProjPm("서현우");
        project.setProjCreateDate(new Date());
        project.setProjDesc("이 프로젝트는 장바구니 결제 로직을 변경하기 위한 것입니다.");
        project.setProjEndDate(new Date());
        project.setProjStartDate(new Date());
        project.setProjectPriority(projectPriority);
        project.setProjectStatus(projectStatus);

        // Contributor 설정 및 프로젝트에 추가
        Contributor contributor = new Contributor();
        contributor.setEmployees(employees);
        contributor.setProject(project);
        List<Contributor> contributors = new ArrayList<>();
        contributors.add(contributor);
        project.setContributors(contributors);

        projectRepository.save(project);
    }

    @Test
    @DisplayName("전체 프로젝트에서 프로젝트명으로 프로젝트 검색")
    void searchByProjTitleInAll() {

        // when
        List<ProjectDTO> foundProjects = searchService.searchProjects("장바구니 결제 로직 변경 요청 건", 1, 1);

        // then
        // 검증: 검색된 프로젝트 리스트는 비어있지 않고, 올바른 프로젝트 타이틀을 가져야 합니다.
        // 결과가 null이 아님을 검증
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");

        // 리스트가 비어있지 않음을 검증
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");

        // 첫 번째 프로젝트의 제목이 예상과 일치하는지 검증
        Assertions.assertTrue(foundProjects.get(0).getProjTitle().equals("장바구니 결제 로직 변경 요청 건"),
                "첫 번째 프로젝트의 제목이 예상과 일치해야 합니다.");
    }

    @Test
    @DisplayName("전체 프로젝트에서 PM으로 프로젝트 검색")
    public void searchByProjPMInAll() {

        // when
        List<ProjectDTO> foundProjects = searchService.searchProjects("서현우", 0, 1);

        // then
        // 검증
        // 결과 리스트가 null이 아닌지 확인
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");

        // 결과 리스트가 비어있지 않은지 확인
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");

        // 첫 번째 프로젝트의 PM이 기대한 값과 일치하는지 확인
        Assertions.assertEquals("서현우", foundProjects.get(0).getProjPm(), "첫 번째 프로젝트의 PM이 기대한 값과 일치해야 합니다.");
    }

    @Test
    @DisplayName("전체 프로젝트에서 참여자로 프로젝트 검색")
    public void searchByContributorInAll() {

        // when
        List<ProjectDTO> foundProjects = searchService.searchProjects("정윤서", 0, 1);

        // then
        // 검증: 검색된 프로젝트 리스트가 null이 아니고 비어있지 않아야 함
        Assertions.assertNotNull(foundProjects, "검색된 프로젝트 리스트는 null이 아니어야 합니다.");
        Assertions.assertFalse(foundProjects.isEmpty(), "검색된 프로젝트 리스트는 비어있지 않아야 합니다.");

        // 검증: 검색된 프로젝트들 중 하나라도 정윤서를 Contributor로 포함하는지 확인
        boolean hasContributor = foundProjects.stream()
                .flatMap(projectDTO -> projectDTO.getContributors().stream())
                .anyMatch(EmployeeDTO -> EmployeeDTO.getName().equals("정윤서"));
        Assertions.assertTrue(hasContributor, "검색된 프로젝트들은 '정윤서'를 Contributor로 포함해야 합니다.");
    }

    // 프로젝트 객체 생성을 돕는 헬퍼 메소드
    private Project createProject(String title, String pm, String contributor) {
        Project project = new Project();
        project.setProjTitle(title);
        project.setProjPm(pm);
        // 프로젝트 생성 로직에 따라 contributor 설정 필요
        return project;
    }
}
