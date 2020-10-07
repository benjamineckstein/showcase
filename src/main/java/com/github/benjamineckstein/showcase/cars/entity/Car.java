package com.github.benjamineckstein.showcase.cars.entity;

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
public class Car extends AbstractUUIDEntity {

    @Column
    private String name;

}
