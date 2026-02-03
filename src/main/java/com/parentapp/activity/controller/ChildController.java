package com.parentapp.activity.controller;

import com.parentapp.activity.dto.CreateChildRequest;
import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.service.ChildService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    // ➕ Add child under a parent
    @PostMapping("/parent/{parentId}")
    public ChildEntity createChildForParent(
            @PathVariable Long parentId,
            @RequestBody CreateChildRequest request) {

        return childService.createChildForParent(parentId, request);
    }

    // 📥 Fetch all children for a parent
    @GetMapping("/parent/{parentId}")
    public List<ChildEntity> getChildrenForParent(
            @PathVariable Long parentId) {

        return childService.getChildrenForParent(parentId);
    }
}
