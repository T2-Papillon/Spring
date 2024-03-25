package com.boogle.papplan.service;

import com.boogle.papplan.entity.Contributor;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.ProjectStatus;
import com.boogle.papplan.repository.ContributorRepository;
import com.boogle.papplan.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ContributorRepository contributorRepository; //참여자

    @Autowired
    public ProjectService(ProjectRepository projectRepository, ContributorRepository contributorRepository) {

        this.projectRepository = projectRepository;
        this.contributorRepository = contributorRepository;
    }

    // PM으로 참여한 프로젝트를 가져오는 메서드
    public List<Project> getProjectsByPM(String projPm) {
        return projectRepository.findByProjPm(projPm);
    }

    // 특정 사용자로 참여한 프로젝트를 가져오는 메서드
    public List<Project> findProjectsByContributorId(Long id) {
        Optional<Contributor> contributorOptional = contributorRepository.findById(id);
        List<Project> projects = new ArrayList<>();
        contributorOptional.ifPresent(contributor -> projects.add(contributor.getProject()));
        return projects;
    }

    // 특정 상태의 프로젝트 목록을 가져오는 메서드
    public List<Project> getProjectsByStatus(String status) {
        // status를 이용하여 적절한 ProjectStatus 객체를 생성하거나 가져와야 함
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.setProjectStatusId(status);
        return projectRepository.findByProjectStatus_ProjectStatusId(projectStatus.getProjectStatusId());
    }


    // 프로젝트명 또는 PM 또는 참여자로 프로젝트 검색
    public List<Project> searchProjects(String searchTerm) {
        return projectRepository.findByTitleOrPmOrContributor(searchTerm);
    }

    // 프로젝트 번호(projNo)에 해당하는 프로젝트를 조회하는 메서드
    public Project getProjectByProjNo(Integer projNo) {
        return projectRepository.findByProjNo(projNo);
    }
}
