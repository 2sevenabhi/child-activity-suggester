package com.parentapp.activity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "child")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ParentEntity parent;

    private String name;

    private Integer age;

    private String interests;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
