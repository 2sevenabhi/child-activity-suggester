package com.parentapp.activity.service;

import com.parentapp.activity.entity.ActivityEntity;
import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.repository.ActivityRepository;
import com.parentapp.activity.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ChildRepository childRepository;

    // existing
    public ActivityEntity save(ActivityEntity activity) {
        return activityRepository.save(activity);
    }

    // ⭐ NEW — suggest by child age
    public List<ActivityEntity> suggestForChild(Long childId) {

        ChildEntity child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("Child not found"));

        return activityRepository
                .findByMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(
                        child.getAge(),
                        child.getAge());
    }
}
