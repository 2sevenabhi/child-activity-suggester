package com.parentapp.activity.ai;

import com.parentapp.activity.entity.ActivityEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RuleBasedAiEngine implements AiEngine {

    @Override
    public ActivityEntity chooseActivity(
            Long childId,
            List<ActivityEntity> candidates,
            String historySummary) {
        return candidates.get(new Random().nextInt(candidates.size()));
    }
}
