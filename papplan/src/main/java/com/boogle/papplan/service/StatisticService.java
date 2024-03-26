package com.boogle.papplan.service;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.interfaces.StatisticProjectDto;
import com.boogle.papplan.interfaces.StatisticTaskStatusDto;
import com.boogle.papplan.repository.StatisticRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {

    @PersistenceContext
    private EntityManager entityManager;
    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    // [통계분석] 특정 프로젝트의 프로젝트 제목, 기간(시작일과 종료일),
    // 참여자, 진행률, 작성일을 조회하는 메서드
    // 복잡한 쿼리문은 criteria api 사용
    public StatisticProjectDto getProjectDetails(Integer projNo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> project = cq.from(Project.class);

        cq.select(project).where(cb.equal(project.get("projNo"), projNo));

        Project result = entityManager.createQuery(cq).getSingleResult();

        // 참여자 정보 처리는 생략됨. 필요한 경우 별도 로직 추가
        return new StatisticProjectDto(
                result.getProjNo(),
                result.getProjTitle(),
                result.getProjStartDate().toString(),
                result.getProjEndDate().toString(),
                result.getProjPercent(),
                result.getProjCreateDate().toString()
        );

    }

    // [프로젝트] 특정 프로젝트의 업무들에 대해 각 진행 상태별로 업무가 몇 개씩 있는지 조회
//    public List<StatisticTaskStatusDto> getTaskCountByStatus(Integer projNo) {
//        return statisticRepository.findTaskCountByStatus(projNo);
//    }
}
