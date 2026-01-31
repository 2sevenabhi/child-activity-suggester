package com.parentapp.activity.scheduler;

import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.repository.ChildRepository;
import com.parentapp.activity.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DailySuggestionScheduler {

    private final ChildRepository childRepository;
    private final RecommendationService recommendationService;

    // this will run at 7pm everyday
    @Scheduled(cron = "0 0 19 * * ?")

    public void generateDailySuggestions() {

        List<ChildEntity> children = childRepository.findAll();

        for (ChildEntity child : children) {
            recommendationService.generateSuggestionForChild(child.getId());
        }
    }
}
