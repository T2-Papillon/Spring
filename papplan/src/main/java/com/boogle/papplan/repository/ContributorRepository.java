package com.boogle.papplan.repository;

import com.boogle.papplan.entity.Contributor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContributorRepository extends JpaRepository<Contributor, Long> {

    // 특정 사용자의 Contributor 엔터티를 가져오는 메서드
    Optional<Contributor> findById(Long id);

}
