package com.parentapp.activity.service;

import com.parentapp.activity.entity.*;
import com.parentapp.activity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.parentapp.activity.ai.AiEngine;
import com.parentapp.activity.ai.RuleBasedAiEngine;
import com.parentapp.activity.ai.ThumbLlmAiEngine;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final DailyActivityLogRepository dailyLogRepo;
    private final ActivityRepository activityRepo;
    private final AiSuggestionRepository aiSuggestionRepo;
    private final RuleBasedAiEngine ruleEngine;
    private final ThumbLlmAiEngine thumbEngine;

    @Value("${ai.mode}")
    private String aiMode;

    public AiSuggestionEntity generateSuggestionForChild(Long childId) {

        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(7);

        // fetch last 7 days logs
        List<DailyActivityLogEntity> logs = dailyLogRepo.findByChildIdAndActivityDateBetween(
                childId,
                weekAgo,
                today);

        Map<String, Long> categoryCount = new HashMap<>();

        for (DailyActivityLogEntity log : logs) {
            String category = log.getActivity().getCategory();
            categoryCount.put(
                    category,
                    categoryCount.getOrDefault(category, 0L) + 1);
        }

        // find least used category
        String leastUsedCategory = categoryCount.entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        List<ActivityEntity> pool;

        if (leastUsedCategory != null) {
            pool = activityRepo.findByCategory(leastUsedCategory);
        } else {
            pool = activityRepo.findAll();
        }

        if (pool.isEmpty()) {
            throw new RuntimeException("No activities found in system. Please seed activity table first.");
        }

        ActivityEntity chosen;

        if ("thumb".equalsIgnoreCase(aiMode)) {
            chosen = thumbEngine.chooseActivity(childId, pool, categoryCount.toString());
        } else {
            chosen = ruleEngine.chooseActivity(childId, pool, categoryCount.toString());
        }

        AiSuggestionEntity suggestion = AiSuggestionEntity.builder()
                .child(
                        ChildEntity.builder()
                                .id(childId)
                                .build())
                .suggestedText(
                        "Try this today: " + chosen.getTitle()
                                + " (" + chosen.getCategory() + ")")
                .createdAt(java.time.LocalDateTime.now())
                .build();

        return aiSuggestionRepo.save(suggestion);
    }
}
