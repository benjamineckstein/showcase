package com.github.benjamineckstein.showcase.expertise.entity;

import com.github.benjamineckstein.showcase.common.AbstractUuidEntity;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
