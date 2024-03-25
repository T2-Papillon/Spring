package com.boogle.papplan.service;

import com.boogle.papplan.entity.Contributor;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.ProjectStatus;
import com.boogle.papplan.repository.ContributorRepository;
import com.boogle.papplan.repository.ProjectListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectListService {

    private final ProjectListRepository projectListRepository;
    private final ContributorRepository contributorRepository;

    @Autowired
    public ProjectListService(ProjectListRepository projectListRepository, ContributorRepository contributorRepository) {
        this.projectListRepository = projectListRepository;
        this.contributorRepository = contributorRepository;
    }

    // PM으로 참여한 프로젝트를 가져오는 메서드
    public List<Project> getProjectsByPM(String projPm) {
        return projectListRepository.findByProjPm(projPm);
    }

    // 특정 사용자로 참여한 프로젝트를 가져오는 메서드
    public List<Project> getProjectsByContributor(Long id) {
        Optional<Contributor> contributorOptional = contributorRepository.findById(id);
        List<Project> projects = new ArrayList<>();
        contributorOptional.ifPresent(contributor -> projects.add(contributor.getProject()));
        return projects;
    }

    // 특정 상태의 프로젝트 목록을 가져오는 메서드
    public List<Project> getProjectsByStatus(ProjectStatus projectStatus) {
        return projectListRepository.findByProjectStatus(projectStatus);
    }

    // 프로젝트명 또는 PM/참여자 이름으로 프로젝트 검색
    public List<Project> searchProjects(String keyword) {
        return projectListRepository.findByProjects(keyword, keyword, keyword);
    }

}
