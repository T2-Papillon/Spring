package com.boogle.papplan.service.search;

import com.boogle.papplan.dto.ProjectDto;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public List<Project> findProjectsByStatusId(String projectStatusId) {
        if (projectStatusId == null || projectStatusId.isEmpty() || projectStatusId.equals("전체")) {
            return searchRepository.findAll();
        } else {
            return searchRepository.findByProjectStatus_ProjectStatusId(projectStatusId);
        }
    }

    // 프로젝트명 또는 PM 또는 참여자로 프로젝트 검색
    @Override
    public List<Project> searchProjects(String searchTerm) {
        return searchRepository.findByTitleOrPmOrContributor(searchTerm);
    }

    @Override
    public List<ProjectDto> searchProjectsDto(String searchTerm) {
        List<Project> projects = searchProjects(searchTerm); // Reuse the searchProjects method
        return projects.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> findProjectsByStatusIdDto(String projectStatusId) {
        List<Project> projects = findProjectsByStatusId(projectStatusId);
        return projects.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ProjectDto convertToDto(Project project) {
        ProjectDto dto = new ProjectDto();
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
