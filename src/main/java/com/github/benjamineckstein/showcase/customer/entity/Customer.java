package com.github.benjamineckstein.showcase.customer.entity;

import com.github.benjamineckstein.showcase.cars.entity.Car;
import com.github.benjamineckstein.showcase.common.AbstractUuidEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@SuperBuilder
public class Customer extends AbstractUuidEntity {

  @Column private String name;

  @OneToMany(mappedBy = "customer")
  private Set<Car> cars;

}
