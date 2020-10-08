package com.github.benjamineckstein.showcase.cars.entity;

import com.github.benjamineckstein.showcase.common.AbstractUuidEntity;
import com.github.benjamineckstein.showcase.customer.entity.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
public class Car extends AbstractUuidEntity {

  @Column private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  Customer customer;
}
