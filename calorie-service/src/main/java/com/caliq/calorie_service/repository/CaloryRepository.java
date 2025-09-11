package com.caliq.calorie_service.repository;

import com.caliq.calorie_service.models.entity.MealEntity;
import com.caliq.calorie_service.models.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CaloryRepository extends JpaRepository<MealEntity,Long> {
    List<MealEntity> findByUser_IdAndDateTimeBetween(
            Long userId,
            LocalDateTime start,
            LocalDateTime end);
    List<MealEntity> findByUserId(Long userId);

    Long user(UserModel user);
}
