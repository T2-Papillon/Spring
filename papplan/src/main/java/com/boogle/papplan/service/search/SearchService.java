package com.boogle.papplan.service.search;

import com.boogle.papplan.dto.employee.EmpSearchDTO;
import com.boogle.papplan.dto.project.ProjectDTO;
import com.boogle.papplan.entity.Project;
import java.util.List;

public interface SearchService {
    List<Project> findProjectsByStatusId(String projectStatusId);
    List<ProjectDTO> findProjectsByStatusIdDto(String projectStatusId);
    List<ProjectDTO> searchProjects(String term, int page, int pageSize);
    List<EmpSearchDTO> findEmployeeByName(String pattern);

}
