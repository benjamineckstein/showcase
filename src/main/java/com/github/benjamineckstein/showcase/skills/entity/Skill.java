package com.github.benjamineckstein.showcase.skills.entity;

import com.github.benjamineckstein.showcase.common.AbstractUuidEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@NoArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
public class Skill extends AbstractUuidEntity {

  @Column private String name;

}
