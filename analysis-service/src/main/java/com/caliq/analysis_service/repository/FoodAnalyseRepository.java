package com.caliq.analysis_service.repository;

import com.caliq.analysis_service.model.ReportsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodAnalyseRepository extends JpaRepository<ReportsEntity, Long> {


}
