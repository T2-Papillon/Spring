package com.boogle.papplan.service;

import com.boogle.papplan.dto.ProjectDto;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<Project> findProjectsByStatusId(String projectStatusId) {
        // 모든 프로젝트를 반환하거나, projectStatusId를 기반으로 필터링
        if (projectStatusId == null || projectStatusId.isEmpty() || projectStatusId.equals("전체")) {
            return searchRepository.findAll();
        } else {
            return searchRepository.findByProjectStatus_ProjectStatusId(projectStatusId);
        }
    }

    // 프로젝트명 또는 PM 또는 참여자로 프로젝트 검색
    public List<Project> searchProjects(String searchTerm) {
        return searchRepository.findByTitleOrPmOrContributor(searchTerm);
    }

    public List<ProjectDto> searchProjectsDto(String searchTerm) {
        List<Project> projects = searchRepository.findByTitleOrPmOrContributor(searchTerm); // 실제 검색 로직
        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects) {
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
            projectDtos.add(dto);
        }
        return projectDtos;
    }

    public List<ProjectDto> findProjectsByStatusIdDto(String projectStatusId) {
        List<Project> projects;
        if (projectStatusId == null || projectStatusId.isEmpty() || projectStatusId.equals("전체")) {
            projects = searchRepository.findAll();
        } else {
            projects = searchRepository.findByProjectStatus_ProjectStatusId(projectStatusId);
        }

        List<ProjectDto> projectDtos = new ArrayList<>();
        for (Project project : projects) {
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
            projectDtos.add(dto);
        }
        return projectDtos;
    }
}