package com.parentapp.activity.controller;

import com.parentapp.activity.entity.ActivityEntity;
import com.parentapp.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    // existing
    @PostMapping
    public ActivityEntity create(@RequestBody ActivityEntity activity) {
        return activityService.save(activity);
    }

    // ⭐ NEW
    @GetMapping("/suggest/{childId}")
    public List<ActivityEntity> suggestActivities(
            @PathVariable Long childId) {

        return activityService.suggestForChild(childId);
    }
}
