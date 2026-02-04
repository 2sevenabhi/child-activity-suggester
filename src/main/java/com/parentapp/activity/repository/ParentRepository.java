package com.parentapp.activity.repository;

import com.parentapp.activity.entity.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentRepository extends JpaRepository<ParentEntity, Long> {

    Optional<ParentEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
