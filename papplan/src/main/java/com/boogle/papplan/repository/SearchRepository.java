package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Project, Long> {
    // projectStatusId를 기반으로 프로젝트를 필터링
    List<Project> findByProjectStatus_ProjectStatusId(String projectStatusId);
}
