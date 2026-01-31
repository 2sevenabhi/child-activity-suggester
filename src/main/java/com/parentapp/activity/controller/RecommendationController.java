package com.parentapp.activity.controller;

import com.parentapp.activity.entity.AiSuggestionEntity;
import com.parentapp.activity.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/{childId}")
    public AiSuggestionEntity generate(@PathVariable Long childId) {
        return recommendationService.generateSuggestionForChild(childId);
    }
}
