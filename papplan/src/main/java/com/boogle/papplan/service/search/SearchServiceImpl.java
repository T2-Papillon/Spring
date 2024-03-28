package com.boogle.papplan.service.search;

import com.boogle.papplan.dto.ProjectDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final ProjectRepository projectRepository;

    @Autowired
    public SearchServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findProjectsByStatusId(String projectStatusId) {
        if (projectStatusId == null || projectStatusId.isEmpty() || projectStatusId.equals("전체")) {
            return projectRepository.findAll();
        } else {
            return projectRepository.findByProjectStatus_ProjectStatusId(projectStatusId);
        }
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
            dto.setProjectStatus(project.getProjectStatus().getProjectStatusId());
        }
        if (project.getProjectPriority() != null) {
            dto.setProjectPriority(project.getProjectPriority().getProjectPriorityId());
        }
        List<Integer> employeeEnos = project.getContributors().stream()
                .map(contributor -> contributor.getEmployees().getEno())
                .collect(Collectors.toList());
        dto.setEmployeeEnos(employeeEnos);

        return dto;
    }
}
