package com.parentapp.activity.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.parentapp.activity.entity.ParentEntity;
import com.parentapp.activity.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentEntity createParent(ParentEntity parent) {

        // ✅ check duplicate email
        if (parentRepository.existsByEmail(parent.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already registered");
        }

        return parentRepository.save(parent);
    }

    public void updateDeviceToken(Long parentId, String token) {

        ParentEntity parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Parent not found"));

        parent.setDeviceToken(token);

        parentRepository.save(parent);
    }

    // 🔐 LOGIN
    public ParentEntity login(String email, String password) {

        ParentEntity parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email"));

        if (!parent.getPassword().equals(password)) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid password");
        }

        return parent;
    }
}
