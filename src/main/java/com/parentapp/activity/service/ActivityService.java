package com.parentapp.activity.service;

import com.parentapp.activity.entity.ActivityEntity;
import com.parentapp.activity.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityEntity save(ActivityEntity activity) {
        return activityRepository.save(activity);
    }
}
