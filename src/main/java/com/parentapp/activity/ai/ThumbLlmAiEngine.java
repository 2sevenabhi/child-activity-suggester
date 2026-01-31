package com.parentapp.activity.ai;

import com.parentapp.activity.entity.ActivityEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Primary // this tells Spring: prefer this over rule-based one
public class ThumbLlmAiEngine implements AiEngine {

    @Value("${thumb.api.url}")
    private String apiUrl;

    @Value("${thumb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public ActivityEntity chooseActivity(
            Long childId,
            List<ActivityEntity> candidates,
            String historySummary) {

        Map<String, Object> body = Map.of(
                "prompt", historySummary,
                "activities", candidates.stream()
                        .map(ActivityEntity::getTitle)
                        .toList());

        try {

            Map response = restTemplate.postForObject(apiUrl, body, Map.class);

            String chosenTitle = response.get("text").toString();

            return candidates.stream()
                    .filter(a -> a.getTitle().equalsIgnoreCase(chosenTitle))
                    .findFirst()
                    .orElse(candidates.get(0));

        } catch (Exception e) {

            System.err.println("Thumb AI failed, falling back to rule engine: " + e.getMessage());

            return candidates.get(new java.util.Random().nextInt(candidates.size()));
        }

    }
}
