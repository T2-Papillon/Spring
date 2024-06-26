package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.project.ProjectCreateDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Contributor;
import com.boogle.papplan.entity.Project;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<ProjectDTO> getProjectsByPM(String projPm);
    List<ProjectDTO> findProjectsByEmpNo(Integer empNo);
    List<ProjectDTO> getProjectsByStatus(String status);
    List<ProjectDTO> searchProjects(String term, int page, int pageSize);
    ProjectDTO getProjectByProjNo(Integer projNo);
    List<ProjectDTO> getAllProjects();

    Optional<HashMap<String,Object>> findProjectsByEmpNoDashBoard(Integer empNo);
    // 프로젝트 진행률 업데이트 메서드
    void updateProjectProgress(Integer projNo);

    void insertProject(ProjectCreateDTO projectDTO);
    void updateProject(ProjectDTO projectDTO);
}