package com.parentapp.activity.ai;

import com.parentapp.activity.entity.ActivityEntity;

import java.util.List;

public interface AiEngine {

    ActivityEntity chooseActivity(
            Long childId,
            List<ActivityEntity> candidates,
            String historySummary);
}
