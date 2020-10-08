package com.github.benjamineckstein.showcase.cars.entity;

import com.github.benjamineckstein.showcase.cars.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CarTest {

  @Autowired CarRepository carRepository;

  @Test
  void testNoArgConstructor() {
    Car testcar = new Car();
    testcar.setName("Name");
    assertThat(testcar.getId()).isNotNull();
  }

  @Test
  void testUUIDIsGenerated() {
    Car testcar = Car.builder().build();
    assertThat(testcar.getId()).isNotNull();
  }

  @Test
  void testIsPersistedForNewEntity() {
    Car testcar = Car.builder().build();
    assertThat(testcar.isPersisted()).isFalse();
    assertThat(testcar.isNew()).isTrue();
  }

  @Test
  void testIsPersistedForOldEntity() {

    Car carSaved = carRepository.saveAndFlush(Car.builder().build());
    assertThat(carSaved.isPersisted()).isTrue();
    assertThat(carSaved.isNew()).isFalse();
  }

  @Test
  void testVersionStartsAtZero() {
    Car testcar = Car.builder().build();
    assertThat(testcar.getVersion()).isNull();

    Car testCarSaved = carRepository.save(testcar);
    assertThat(testcar.getVersion()).isEqualTo(0);
  }

  @Test
  void testVersionIncrements() {
    Car testCarSaved = carRepository.saveAndFlush(Car.builder().build());
    testCarSaved.setName("Test123");

    assertThat(carRepository.saveAndFlush(testCarSaved).getVersion()).isGreaterThan(0);
  }
}
