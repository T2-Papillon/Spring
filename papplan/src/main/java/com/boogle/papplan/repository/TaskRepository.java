package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Project;
import com.boogle.papplan.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    // 프로젝트 상세페이지에 대한 댓글들을 조회하는 메서드
    List<Task> findAllByProject(Project project);
}