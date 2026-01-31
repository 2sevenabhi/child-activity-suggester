package com.parentapp.activity.scheduler;

import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.repository.ChildRepository;
import com.parentapp.activity.repository.AiSuggestionRepository;
import com.parentapp.activity.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailySuggestionScheduler {

    private final ChildRepository childRepository;
    private final RecommendationService recommendationService;
    private final AiSuggestionRepository aiSuggestionRepository;

    // Runs every day at 7 PM IST
    @Scheduled(cron = "*/30 * * * * ?", zone = "Asia/Kolkata")

    public void generateDailySuggestions() {

        log.info("🌙 Evening scheduler started...");

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));

        List<ChildEntity> children = childRepository.findAll();

        for (ChildEntity child : children) {

            try {

                boolean alreadySuggested = aiSuggestionRepository
                        .existsByChildIdAndCreatedAtBetween(
                                child.getId(),
                                today.atStartOfDay(),
                                today.plusDays(1).atStartOfDay());

                if (alreadySuggested) {
                    log.info("⏭ Skipping child {} — already suggested today", child.getId());
                    continue;
                }

                log.info("🤖 Generating suggestion for child {}", child.getId());

                recommendationService.generateSuggestionForChild(child.getId());

            } catch (Exception e) {

                log.error("❌ Failed to generate for child {} : {}", child.getId(), e.getMessage());

            }
        }

        log.info("✅ Evening scheduler finished.");
    }
}
