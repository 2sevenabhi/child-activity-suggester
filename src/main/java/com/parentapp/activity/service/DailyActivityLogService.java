package com.parentapp.activity.service;

import com.parentapp.activity.entity.ActivityEntity;
import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.entity.DailyActivityLogEntity;
import com.parentapp.activity.repository.ActivityRepository;
import com.parentapp.activity.repository.ChildRepository;
import com.parentapp.activity.repository.DailyActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyActivityLogService {

    private final DailyActivityLogRepository dailyActivityLogRepository;
    private final ChildRepository childRepository;
    private final ActivityRepository activityRepository;

    // ✅ SAVE TODAY'S COMPLETED ACTIVITY
    public void logActivity(Long childId, Long activityId) {

        ChildEntity child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        ActivityEntity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        DailyActivityLogEntity log = DailyActivityLogEntity.builder()
                .child(child)
                .activity(activity)
                .activityDate(LocalDate.now())
                .build();

        dailyActivityLogRepository.save(log);
    }
}
