package com.boogle.papplan.service;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Override
    public List<Project> searchProjects(String keyword) {
        // 프로젝트명, PM, 참여자 이름에 키워드가 포함된 프로젝트 검색
        return searchRepository.findByProjects(keyword, keyword, keyword);
    }

    @Override
    public List<Project> filterProjectsByStatus(String projectStatusId) {
        // 상태에 따라 필터링된 프로젝트 목록 반환
        return searchRepository.findByProjectStatusId(projectStatusId);
    }

    @Override
    public List<Project> sortByLatestProjects() {
        // 최신순으로 정렬된 프로젝트 목록 반환
        return searchRepository.findByOrderByCreateDateDesc();
    }

    @Override
    public List<Project> sortByPriorityProjects() {
        // 우선순위순으로 정렬된 프로젝트 목록 반환
        return searchRepository.findByOrderByPriorityLevelDesc();
    }

    @Override
    public List<Project> getAllProjects() {
        // 모든 프로젝트를 반환
        return searchRepository.findAll();
    }


}
