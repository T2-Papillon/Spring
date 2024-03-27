package com.boogle.papplan.service.search;

import com.boogle.papplan.dto.ProjectDto;
import com.boogle.papplan.entity.Project;
import java.util.List;

public interface SearchService {
    List<Project> findProjectsByStatusId(String projectStatusId);
    List<Project> searchProjects(String searchTerm);
    List<ProjectDto> searchProjectsDto(String searchTerm);
    List<ProjectDto> findProjectsByStatusIdDto(String projectStatusId);
}