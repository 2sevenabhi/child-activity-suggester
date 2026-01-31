package com.parentapp.activity.repository;

import com.parentapp.activity.entity.DailyActivityLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DailyActivityLogRepository extends JpaRepository<DailyActivityLogEntity, Long> {

    List<DailyActivityLogEntity> findByChildIdAndActivityDateBetween(
            Long childId,
            LocalDate start,
            LocalDate end);
}
