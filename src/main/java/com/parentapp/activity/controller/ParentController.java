package com.parentapp.activity.controller;

import com.parentapp.activity.dto.DeviceTokenRequest;
import com.parentapp.activity.entity.ParentEntity;
import com.parentapp.activity.service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parents")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping
    public ParentEntity createParent(@RequestBody ParentEntity parent) {
        return parentService.createParent(parent);
    }

    // 🔔 Save device token for push notifications
    @PostMapping("/{id}/device-token")
    public void saveDeviceToken(
            @PathVariable Long id,
            @RequestBody DeviceTokenRequest request) {
        parentService.updateDeviceToken(id, request.getToken());
    }
}
