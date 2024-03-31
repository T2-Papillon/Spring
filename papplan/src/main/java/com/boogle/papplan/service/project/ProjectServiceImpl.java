package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.EmployeeDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.ContributorRepository;
import com.boogle.papplan.repository.EmployeeRepository;
import com.boogle.papplan.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ContributorRepository contributorRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ContributorRepository contributorRepository,
                              EmployeeRepository employeeRepository) {
        this.projectRepository = projectRepository;
        this.contributorRepository = contributorRepository;
        this.employeeRepository = employeeRepository;
    }

    // PM으로 참여한 프로젝트를 가져오는 메서드
    @Override
    public List<ProjectDTO> getProjectsByPM(String projPm) {
        return projectRepository.findByProjPm(projPm).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 특정 사용자로 참여한 프로젝트를 가져오는 메서드
    @Override
    public List<ProjectDTO> findProjectsByEmpNo(Integer empNo) {
        return projectRepository.findAllByEmpno(empNo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    // 특정 상태의 프로젝트 목록을 가져오는 메서드
    @Override
    public List<ProjectDTO> getProjectsByStatus(String status) {
        return projectRepository.findByProjectStatus_ProjectStatusId(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 프로젝트명 또는 PM 또는 참여자로 프로젝트 검색
    @Override
    public List<ProjectDTO> searchProjects(String term, int page, int pageSize) {
        return projectRepository.findByTermWithPage(term, PageRequest.of(page, pageSize)).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 프로젝트 번호로 프로젝트 상세정보 조회
    @Override
    public ProjectDTO getProjectByProjNo(Integer projNo) {
        Optional<Project> project = projectRepository.findById(projNo);
        return project.map(this::convertToDto).orElse(null);
    }

    // 모든 프로젝트 목록 조회
    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 프로젝트 엔티티를 ProjectDto로 변환하는 유틸리티 메소드
    private ProjectDTO convertToDto(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setProjNo(project.getProjNo());
        dto.setProjTitle(project.getProjTitle());
        dto.setProjPm(project.getProjPm());
        dto.setProjStartDate(project.getProjStartDate());
        dto.setProjEndDate(project.getProjEndDate());
        dto.setProjPercent(project.getProjPercent());
        dto.setProjCreateDate(project.getProjCreateDate());
        dto.setProjDesc(project.getProjDesc());
        if (project.getProjectStatus() != null) {
            dto.setProjectStatus(project.getProjectStatus().getProjectStatusId()); // Or use getProjectStatusName()
        }
        if (project.getProjectPriority() != null) {
            dto.setProjectPriority(project.getProjectPriority().getProjectPriorityId()); // Or use getProjectPriorityName()
        }
        // 참여자 정보 설정
        List<Integer> contributorEnos = project.getContributors().stream()
                .map(contributor -> contributor.getEmployees().getEno())
                .collect(Collectors.toList());
        Optional<List<EmployeeDTO>> contributors = employeeRepository.findAllByEnos(contributorEnos);
        contributors.ifPresent(dto::setContributors);
        return dto;
    }
}