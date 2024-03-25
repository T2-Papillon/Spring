package com.boogle.papplan.service;

import com.boogle.papplan.entity.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract interface SearchService {

    // 키워드를 사용하여 프로젝트를 검색하는 메서드
    public List<Project> searchProjects(String keyword);

    // 상태에 따라 프로젝트를 필터링하는 메서드
    public abstract List<Project> filterProjectsByStatus(String projectStatusId);

    // 최신순으로 프로젝트를 정렬하는 메서드
    public abstract List<Project> sortByLatestProjects();

    // 우선순위에 따라 프로젝트를 정렬하는 메서드
    public abstract List<Project> sortByPriorityProjects();

    // 기본값으로 모든 프로젝트를 반환하는 메서드
    public List<Project> getAllProjects();


}
