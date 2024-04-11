package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.EmployeeDTO;
import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Contributor;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.ContributorRepository;
import com.boogle.papplan.repository.EmployeeRepository;
import com.boogle.papplan.repository.ProjectRepository;
import com.boogle.papplan.repository.TaskRepository;
import com.boogle.papplan.service.task.TaskService;
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
    private final TaskService taskService;


    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              ContributorRepository contributorRepository,
                              EmployeeRepository employeeRepository,
                              TaskService taskService) {
        this.projectRepository = projectRepository;
        this.contributorRepository = contributorRepository;
        this.employeeRepository = employeeRepository;
        this.taskService = taskService;
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


    @Override
    public void updateProjectProgress(Integer projNo) {
        //하위업무 조회
        List<TaskDTO> tasks = taskService.getTasksByProjectId(projNo);

        if (!tasks.isEmpty()) {
            // 가져온 작업들의 진행률을 평균냄
            double averageProgress = tasks.stream()
                    .mapToInt(TaskDTO::getTaskPercent)
                    .average()
                    .orElse(0.0); // 작업이 없으면 0 반환

            // 프로젝트 엔티티를 찾아서 진행률을 업데이트
            Optional<Project> projectOptional = projectRepository.findById(projNo);
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                project.setProjPercent((int) Math.round(averageProgress));
                projectRepository.save(project);
            }
        }
    }

    // 24.04.11 프로젝트 생성
    @Override
    public void insertProject(Project project, List<Contributor> contributors) {
        projectRepository.save(project);
        contributorRepository.saveAll(contributors);
    }

    // 24.04.11 프로젝트 내용 수정
    @Override
    public void updateProject(ProjectDTO projectDTO) {

    }

    // 프로젝트 엔티티를 ProjectDto로 변환하는 유틸리티 메소드
    private ProjectDTO convertToDto(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setProjNo(project.getProjNo());
        dto.setProjTitle(project.getProjTitle());
        if(project.getProjPm() != null) {
            dto.setProjPm(project.getProjPm().getName());
        }
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