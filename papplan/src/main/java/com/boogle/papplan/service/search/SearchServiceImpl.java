package com.boogle.papplan.service.search;

import com.boogle.papplan.dto.employee.EmpSearchDTO;
import com.boogle.papplan.dto.employee.EmployeeDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.ContributorRepository;
import com.boogle.papplan.repository.EmployeeRepository;
import com.boogle.papplan.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ContributorRepository contributorRepository;

    @Autowired
    public SearchServiceImpl(ProjectRepository projectRepository,
                             EmployeeRepository employeeRepository,
                             ContributorRepository contributorRepository)
    {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.contributorRepository = contributorRepository;
    }

    @Override
    public List<Project> findProjectsByStatusId(String projectStatusId) {
        if (!isValidStatus(projectStatusId)) {
            throw new IllegalArgumentException("유효하지 않은 상태값입니다.");
        }

        if (projectStatusId.equals("전체")) {
            return projectRepository.findAll();
        } else {
            return projectRepository.findByProjectStatus_ProjectStatusId(projectStatusId);
        }
    }

    private boolean isValidStatus(String projectStatusId) {
        // 프로젝트 상태값이 유효한지 데이터베이스에서 조회하여 확인
        return projectRepository.existsByProjectStatus_ProjectStatusId(projectStatusId);
    }

    // 프로젝트명 또는 PM 또는 참여자로 프로젝트 검색
    @Override
    public List<ProjectDTO> searchProjects(String term, int page, int pageSize) {
        List<Project> projects = projectRepository.findByTermWithPage(term, PageRequest.of(page,pageSize));
        return projects.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> findProjectsByStatusIdDto(String projectStatusId) {
        List<Project> projects = findProjectsByStatusId(projectStatusId);
        return projects.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<EmpSearchDTO> findEmployeeByName(String pattern) {
        Optional<List<EmpSearchDTO>> empList = employeeRepository.findAllByPattern(pattern);

        return empList.orElseGet(ArrayList::new);
    }

    // 프로젝트 정보 조회 + 참여중인 직원 정보 조회
    private ProjectDTO convertToDto(Project project) {
        ProjectDTO dto = new ProjectDTO();

        dto.setProjNo(project.getProjNo());
        dto.setProjTitle(project.getProjTitle());
        dto.setProjPm(project.getProjPm().getName());
        dto.setProjStartDate(project.getProjStartDate());
        dto.setProjEndDate(project.getProjEndDate());
        dto.setProjPercent(project.getProjPercent());
        dto.setProjCreateDate(project.getProjCreateDate());
        dto.setProjDesc(project.getProjDesc());

        if (project.getProjectStatus() != null) {
            dto.setProjectStatus(project.getProjectStatus().getProjectStatusId());
        }
        if (project.getProjectPriority() != null) {
            dto.setProjectPriority(project.getProjectPriority().getProjectPriorityId());
        }

        /*List<Integer> contributorEnos = project.getContributors().stream()
                .map(contributor -> contributor.getEmployees().getEno())
                .collect(Collectors.toList());
        List<EmployeeDTO> contributors = contributorRepository.findContributors(project.getProjNo());
        dto.setContributors(contributors);*/
        List<EmployeeDTO> contributors = contributorRepository.findContributors(project.getProjNo());
        dto.setContributors(contributors);

        return dto;
    }
}