package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.ProjectDto;
import java.util.List;

public interface ProjectService {

    List<ProjectDto> getProjectsByPM(String projPm);
    List<ProjectDto> findProjectsByContributorId(Long id);
    List<ProjectDto> getProjectsByStatus(String status);
    List<ProjectDto> searchProjects(String searchTerm);
    ProjectDto getProjectByProjNo(Integer projNo);
    List<ProjectDto> getAllProjects();

}
