package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.project.ProjectCreateDTO;
import com.boogle.papplan.entity.*;
import com.boogle.papplan.repository.ContributorRepository;
import com.boogle.papplan.repository.EmployeeRepository;
import com.boogle.papplan.repository.ProjectRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProjectCreateServiceImplTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ContributorRepository contributorRepository;

    Employees employees;
    List<Integer> contributors;

    @BeforeEach
    void beforeEmployee() {

        Department department = new Department();   department.setDept_no("PL");
        Position position = new Position();         position.setPosition_id("TEAM_LEADER");

        employees = new Employees(1, "test@boogle.com", "1234", "홍길동", department, position);
        contributors = new ArrayList<>();   contributors.add(1);
    }

    @Test
    @Transactional
    @DisplayName("프로젝트 생성 테스트")
    public void 프로젝트_생성() {

        // given
        employeeRepository.save(employees);

        ProjectCreateDTO createDTO = new ProjectCreateDTO();
        createDTO.setProjTitle("테스트 타이틀-1");
        createDTO.setProjPmEno(1);
        createDTO.setProjStartDate(new Date(2024, Calendar.APRIL,15));
        createDTO.setProjEndDate(new Date(2024,Calendar.APRIL,30));
        createDTO.setProjCreateDate(new Date());
        createDTO.setProjDesc("");
        createDTO.setProjectPriority("LV1");
        createDTO.setProjectStatus("TODO");
        createDTO.setContributors(contributors);

        // when
        projectService.insertProject(createDTO);

        // then
        Optional<Project> project = projectRepository.findById(1);
        List<Contributor> contributors = contributorRepository.findAll();

        assertThat(project.get().getProjTitle()).isEqualTo("테스트 타이틀-1");
        assertThat(contributors).hasSize(1);
    }
}