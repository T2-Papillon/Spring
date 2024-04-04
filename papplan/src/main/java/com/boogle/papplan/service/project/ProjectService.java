package com.boogle.papplan.service.project;

import com.boogle.papplan.dto.project.ProjectDTO;
import java.util.List;

public interface ProjectService {

    List<ProjectDTO> getProjectsByPM(String projPm);
    List<ProjectDTO> findProjectsByEmpNo(Integer empNo);
    List<ProjectDTO> getProjectsByStatus(String status);
    List<ProjectDTO> searchProjects(String term, int page, int pageSize);
    ProjectDTO getProjectByProjNo(Integer projNo);
    List<ProjectDTO> getAllProjects();
    void updateProjectProgress(Integer projNo); // 프로젝트 진행율 업데이트 메서드 추가

}