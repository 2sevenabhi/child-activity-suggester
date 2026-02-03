package com.parentapp.activity.service;

import com.parentapp.activity.entity.ParentEntity;
import com.parentapp.activity.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentEntity createParent(ParentEntity parent) {
        return parentRepository.save(parent);
    }

    public void updateDeviceToken(Long parentId, String token) {

        ParentEntity parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        parent.setDeviceToken(token);

        parentRepository.save(parent);
    }

    // 🔐 LOGIN
    public ParentEntity login(String email, String password) {

        ParentEntity parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (!parent.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return parent;
    }
}
