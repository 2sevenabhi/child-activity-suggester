package com.parentapp.activity.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_activity_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyActivityLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private ChildEntity child;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activity;

    @Column(name = "activity_date")
    private LocalDate activityDate;
}
