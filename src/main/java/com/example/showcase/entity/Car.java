package com.example.showcase.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Generated;

import lombok.Data;

@Data
@Entity
public class Car {

    @Id
    private Long id;

    @Column
    private String name;

}
