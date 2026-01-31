package com.parentapp.activity.repository;

import com.parentapp.activity.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    List<ActivityEntity> findByCategory(String category);

    List<ActivityEntity> findByMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(Integer age1, Integer age2);
}
