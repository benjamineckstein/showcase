package com.example.showcase.cars.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@SuperBuilder
public class Car extends AbstractUUIDEntity {

    @Column
    private String name;

    @Version
    @Column
    private Integer version;

}
