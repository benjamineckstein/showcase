package com.example.showcase.cars.entity;

import lombok.*;
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
