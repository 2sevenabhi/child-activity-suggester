package com.parentapp.activity.service;

import com.parentapp.activity.dto.CreateChildRequest;
import com.parentapp.activity.entity.ChildEntity;
import com.parentapp.activity.entity.ParentEntity;
import com.parentapp.activity.repository.ChildRepository;
import com.parentapp.activity.repository.ParentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    private final ParentRepository parentRepository;

    public ChildEntity createChildForParent(Long parentId, CreateChildRequest request) {

        ParentEntity parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        ChildEntity child = ChildEntity.builder()
                .parent(parent)
                .name(request.getName())
                .age(request.getAge())
                .interests(request.getInterests())
                .createdAt(LocalDateTime.now())
                .build();

        return childRepository.save(child);
    }

    public List<ChildEntity> getChildrenForParent(Long parentId) {
        return childRepository.findByParentId(parentId);
    }
}
