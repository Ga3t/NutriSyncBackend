package com.caliq.analysis_service.repository;

import com.caliq.analysis_service.model.WeightLogsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeightLogsRepository extends JpaRepository<WeightLogsEntity, Long> {

    Optional<WeightLogsEntity> findByUserIdAndWeighingDate(Long userId, LocalDate time);
}
