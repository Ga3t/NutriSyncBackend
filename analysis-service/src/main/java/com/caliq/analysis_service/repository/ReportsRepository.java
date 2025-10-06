package com.caliq.analysis_service.repository;

import com.caliq.analysis_service.model.ReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReportsRepository extends JpaRepository<ReportsEntity, Long> {
    Optional<ReportsEntity> findByUserIdAndDate(Long userId, LocalDate date);
}
