package com.boogle.papplan.service;

import com.boogle.papplan.dto.StatisticCombinedDTO;
import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.Task;
import com.boogle.papplan.entity.TaskStatus;
import com.boogle.papplan.dto.StatisticProjectDTO;
import com.boogle.papplan.dto.StatisticTaskStatusDTO;
import com.boogle.papplan.repository.StatisticRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public StatisticProjectDTO getProjectDetails(Integer projNo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> cq = cb.createQuery(Project.class);
        Root<Project> project = cq.from(Project.class);

        cq.select(project).where(cb.equal(project.get("projNo"), projNo));

        Project result = entityManager.createQuery(cq).getSingleResult();

        // 참여자 정보 처리는 생략됨. 필요한 경우 별도 로직 추가
        return new StatisticProjectDTO(
                result.getProjNo(),
                result.getProjTitle(),
                result.getProjStartDate().toString(),
                result.getProjEndDate().toString(),
                result.getProjPercent(),
                result.getProjCreateDate().toString()
        );

    }

    // [프로젝트] 특정 프로젝트의 업무들에 대해 각 진행 상태별로 업무가 몇 개씩 있는지 조회
    public List<StatisticTaskStatusDTO> getTaskCountByStatus(Integer projNo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createTupleQuery();
        Root<Project> project = cq.from(Project.class);
        Join<Project, Task> tasks = project.join("tasks", JoinType.INNER); // 'tasks'는 Project 엔터티에 정의된 Task 엔터티와의 관계를 나타냅니다.
        Join<Task, TaskStatus> taskStatus = tasks.join("taskStatus", JoinType.INNER); // 'taskStatus'는 Task 엔터티 내에 정의된 TaskStatus 엔터티와의 관계를 나타냅니다.

        cq.multiselect(taskStatus.get("name").alias("taskStatusName"),
                        cb.count(tasks).alias("taskCount"))
                .where(cb.equal(project.get("projNo"), projNo))
                .groupBy(taskStatus.get("name"));

        List<Tuple> results = entityManager.createQuery(cq).getResultList();

        return results.stream().map(t -> new StatisticTaskStatusDTO(
                t.get("taskStatusName", String.class),
                t.get("taskCount", Long.class)
        )).collect(Collectors.toList());
    }

    public StatisticCombinedDTO getCombinedProjectInfo(Integer projNo) {
        StatisticProjectDTO projectDetails = getProjectDetails(projNo); // 기존 메서드 사용
        List<StatisticTaskStatusDTO> taskStatusCounts = getTaskCountByStatus(projNo); // 기존 메서드 사용
        return new StatisticCombinedDTO(projectDetails, taskStatusCounts);
    }
}
