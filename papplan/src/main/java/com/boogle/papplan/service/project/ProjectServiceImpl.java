package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.employee.EmployeeDTO;
import com.boogle.papplan.dto.TaskDTO;
import com.boogle.papplan.dto.project.ProjectCreateDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.*;
import com.boogle.papplan.repository.*;
import com.boogle.papplan.service.task.TaskService;
import com.boogle.papplan.util.DashBoardDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public Optional<HashMap<String,Object>> findProjectsByEmpNoDashBoard(Integer empNo) {
        HashMap<String, Object> projectData;
        List<Project> doingProjects;
        List<ProjectDTO> doingProjectDTOs = new ArrayList<>();
        try {
            projectData = DashBoardDataUtil.getDashBoardPrjData(projectRepository.findAllByEmpno(empNo));
            doingProjects = (List<Project>) projectData.get("projects");
            for(Project project : doingProjects) {
                doingProjectDTOs.add(convertToDto(project));
            }

            projectData.put("projects", doingProjectDTOs);
        }
        catch(Exception e) {
            return Optional.empty();
        }

        return Optional.of(projectData);


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
        Optional<Project> project = projectRepository.findByProjNo(projNo);
        return project.map(this::convertToDto).orElse(null);
    }

    // 모든 프로젝트 목록 조회
    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAllWithLazy().stream()
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
    public void insertProject(ProjectCreateDTO projectDto) {
        Project project = convertToProject(projectDto);
        Project savePrj = projectRepository.save(project);
        insertContributor(savePrj.getProjNo(), projectDto.getContributors());
    }

    // 24.04.11 프로젝트 내용 수정
    @Override
    public void updateProject(ProjectDTO projectDTO) {

    }

    // 조회 결과가 단일일 경우 응답 DTO 객체 생성 메소드
    private ProjectDTO convertToDto(Project project) {
        ProjectDTO dto = getBaseProjectDTO(project);

        /*// 참여자 정보 설정
        List<Integer> contributorEnos = project.getContributors().stream()
                .map(contributor -> contributor.getEmployees().getEno())
                .collect(Collectors.toList());
        Optional<List<EmployeeDTO>> contributors = employeeRepository.findAllByEnos(contributorEnos);*/
        //contributors.ifPresent(dto::setContributors);
        List<EmployeeDTO> contributors = contributorRepository.findContributors(project.getProjNo());
        dto.setContributors(contributors);

        return dto;
    }

    // 기본 DTO 객체 형태를 만든다.
    ProjectDTO getBaseProjectDTO(Project project) {
        ProjectDTO dto = new ProjectDTO();
        dto.setProjNo(project.getProjNo());
        dto.setProjTitle(project.getProjTitle());
        if(project.getProjPm() != null) {
            dto.setProjPm(project.getProjPm().getName());
            dto.setProjPmDept(project.getProjPm().getDepartment().getDept_no());
            dto.setProjPmEno(project.getProjPm().getEno());
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

        return dto;
    }

    // Project 생성 DTO -> Project Entity
    Project convertToProject(ProjectCreateDTO projectDto) {
        Project project = new Project();

        project.setProjTitle(projectDto.getProjTitle());

        Employees pm = new Employees();
        pm.setEno(projectDto.getProjPmEno());
        project.setProjPm(pm);

        project.setProjStartDate(projectDto.getProjStartDate());
        project.setProjEndDate(projectDto.getProjEndDate());
        project.setProjCreateDate(projectDto.getProjCreateDate());
        project.setProjDesc(projectDto.getProjDesc());

        ProjectPriority projectPriority = new ProjectPriority();
        projectPriority.setProjectPriorityId(projectDto.getProjectPriority());
        project.setProjectPriority(projectPriority);

        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setProjectStatusId(projectDto.getProjectStatus());
        project.setProjectStatus(projectStatus);

        return project;
    }

    // Project 생성 시 참여자 목록 추출 후 DB 저장
    public void insertContributor(int projNo, List<Integer> contributors) {
        List<Contributor> newContributors = new ArrayList<>();
        for(int n : contributors) {
            Contributor contributor = new Contributor();
            Project project = new Project();
            project.setProjNo(projNo);
            contributor.setProject(project);
            Employees employees = new Employees();
            employees.setEno(n);
            contributor.setEmployees(employees);
            newContributors.add(contributor);
        }
        contributorRepository.saveAll(newContributors);
    }
}