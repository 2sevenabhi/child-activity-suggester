package com.parentapp.activity.service;

import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.repository.ChildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    public ChildEntity createChild(ChildEntity child) {
        return childRepository.save(child);
    }
}
