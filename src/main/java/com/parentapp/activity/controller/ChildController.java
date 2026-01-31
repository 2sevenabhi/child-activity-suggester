package com.parentapp.activity.controller;

import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.service.ChildService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/children")
@RequiredArgsConstructor
public class ChildController {

    private final ChildService childService;

    @PostMapping
    public ChildEntity createChild(@RequestBody ChildEntity child) {
        return childService.createChild(child);
    }
}
