package com.boogle.papplan.service;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SearchService {

    private final SearchRepository searchRepository;

    @Autowired
    public SearchService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public List<Project> findProjectsByStatusId(String projectStatusId) {
        // 모든 프로젝트를 반환하거나, projectStatusId를 기반으로 필터링
        if (projectStatusId == null || projectStatusId.isEmpty() || projectStatusId.equals("전체")) {
            return searchRepository.findAll();
        } else {
            return searchRepository.findByProjectStatus_ProjectStatusId(projectStatusId);
        }
    }
}
