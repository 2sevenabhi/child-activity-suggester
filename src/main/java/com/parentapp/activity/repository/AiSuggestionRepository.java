package com.parentapp.activity.repository;

import com.parentapp.activity.entity.AiSuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AiSuggestionRepository extends JpaRepository<AiSuggestionEntity, Long> {

    boolean existsByChildIdAndCreatedAtBetween(
            Long childId,
            LocalDateTime start,
            LocalDateTime end);
}
