package com.parentapp.activity.repository;

import com.parentapp.activity.entity.ChildEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildRepository extends JpaRepository<ChildEntity, Long> {

    List<ChildEntity> findByParentId(Long parentId);
}
