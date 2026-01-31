package com.parentapp.activity.repository;

import com.parentapp.activity.entity.AiSuggestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiSuggestionRepository extends JpaRepository<AiSuggestionEntity, Long> {
}
