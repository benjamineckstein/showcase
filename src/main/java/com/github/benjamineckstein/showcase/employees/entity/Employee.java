package com.github.benjamineckstein.showcase.employees.entity;

import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.common.AbstractUuidEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
public class Employee extends AbstractUuidEntity {

  @Column private String name;

  @OneToMany(mappedBy = "employee")
  private Set<Expertise> expertise;

}
