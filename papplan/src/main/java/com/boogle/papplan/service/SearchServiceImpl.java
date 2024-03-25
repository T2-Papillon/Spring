package com.boogle.papplan.service;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
        return searchRepository.findByKeyword(keyword);
    }

    @Override
    public List<Project> filterProjectsByStatus(List<String> statusIds) {
        return searchRepository.findByProjectStatusIdIn(statusIds);
    }

    @Override
    public List<Project> sortBy(String orderBy) {
        // orderBy가 비어있거나 유효하지 않은 경우, 'createDate'로 기본 설정
        if (orderBy == null || orderBy.isEmpty()) {
            orderBy = "createDate"; // 기본 정렬 기준 설정
        }
        Sort sort = Sort.by(orderBy).descending(); // 최신 프로젝트부터 나타나도록 내림차순 설정
        return searchRepository.findAll(sort);
    }

//    @Override
//    public List<Project> sortByLatestProjects() {
//        // 최신순으로 정렬된 프로젝트 목록 반환
//        return searchRepository.findByOrderByCreateDateDesc();
//    }
//
//    @Override
//    public List<Project> sortByPriorityProjects() {
//        // 우선순위순으로 정렬된 프로젝트 목록 반환
//        return searchRepository.findByOrderByPriorityLevelDesc();
//    }

    @Override
    public List<Project> getAllProjects() {
        // 모든 프로젝트를 반환
        return searchRepository.findAll();
    }

}