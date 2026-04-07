package com.github.benjamineckstein.showcase.expertise.entity;

import com.github.benjamineckstein.showcase.common.AbstractUuidEntity;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@NoArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
public class Expertise extends AbstractUuidEntity {

  @Column private String description;

  @Enumerated(EnumType.STRING)
  @Column
  private ExpertiseLevel level;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee;

  @NonNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "skill_id", nullable = false)
  private Skill skill;
}
