package com.parentapp.activity.scheduler;

import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.repository.ChildRepository;
import com.parentapp.activity.repository.AiSuggestionRepository;
import com.parentapp.activity.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.parentapp.activity.service.NotificationService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailySuggestionScheduler {

    private final ChildRepository childRepository;
    private final RecommendationService recommendationService;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 * * * * ?")
    public void generateDailySuggestions() {

        List<ChildEntity> children = childRepository.findAll();

        for (ChildEntity child : children) {

            var suggestion = recommendationService.generateSuggestionForChild(child.getId());

            notificationService.sendToParent(
                    child.getParent().getId(),
                    suggestion.getSuggestedText());
        }
    }
}
