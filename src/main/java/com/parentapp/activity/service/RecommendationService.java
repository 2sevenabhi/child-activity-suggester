package com.parentapp.activity.service;

import com.parentapp.activity.entity.*;
import com.parentapp.activity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final DailyActivityLogRepository dailyLogRepo;
    private final ActivityRepository activityRepo;
    private final AiSuggestionRepository aiSuggestionRepo;

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

        ActivityEntity chosen = pool.get(new Random().nextInt(pool.size()));

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
