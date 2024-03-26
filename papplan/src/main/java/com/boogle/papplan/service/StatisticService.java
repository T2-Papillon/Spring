package com.boogle.papplan.service;

import com.boogle.papplan.dto.StatisticProjectDto;
import com.boogle.papplan.dto.StatisticTaskStatusDto;
import com.boogle.papplan.repository.ProjectRepository;
import com.boogle.papplan.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    // [통계분석] 특정 프로젝트의 프로젝트 제목, 기간(시작일과 종료일),
    // 참여자, 진행률, 작성일을 조회하는 메서드
    public StatisticProjectDto getProjectDetails(Integer projNo) {
        return statisticRepository.findProjectDetailsByProjNo(projNo);
    }

    // [프로젝트] 특정 프로젝트의 업무들에 대해 각 진행 상태별로 업무가 몇 개씩 있는지 조회
    public List<StatisticTaskStatusDto> getTaskCountByStatus(Integer projNo) {
        return statisticRepository.findTaskCountByStatus(projNo);
    }
}
