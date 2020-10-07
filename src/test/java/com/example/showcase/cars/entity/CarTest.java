package com.example.showcase.cars.entity;

import com.example.showcase.cars.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CarTest {

    @Autowired
    CarRepository carRepository;

    @Test
    void testUUIDIsGenerated() {
        Car testcar = Car.builder().build();
        assertThat(testcar.getId()).isNotNull();
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