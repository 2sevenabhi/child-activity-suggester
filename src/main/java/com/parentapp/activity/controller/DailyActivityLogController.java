package com.parentapp.activity.controller;

import com.parentapp.activity.service.DailyActivityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/daily-log")
@RequiredArgsConstructor
public class DailyActivityLogController {

    private final DailyActivityLogService dailyActivityLogService;

    // ✅ called from mobile checkbox

    @PostMapping
    public void logDailyActivity(
            @RequestParam Long childId,
            @RequestParam Long activityId) {

        dailyActivityLogService.logActivity(childId, activityId);
    }
}
