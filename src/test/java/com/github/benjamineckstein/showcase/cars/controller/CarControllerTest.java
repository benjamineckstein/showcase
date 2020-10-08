package com.github.benjamineckstein.showcase.cars.controller;

import com.github.benjamineckstein.showcase.cars.entity.Car;
import com.github.benjamineckstein.showcase.cars.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CarControllerTest {

  private @Autowired CarController carController;
  private @Autowired CarRepository carRepository;

  @Test
  void testCreateCarResponse() {

    ResponseEntity<Car> carCreatedResponse = carController.createCar("TestName");
    assertThat(carCreatedResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(carCreatedResponse.getHeaders().getLocation())
        .isNotNull()
        .hasPath("/api/cars/TestName");
  }

  @Test
  void testCreateCarPersisted() {

    ResponseEntity<Car> carCreatedResponse = carController.createCar("TestName");
    assertThat(carRepository.findAll()).isNotEmpty().containsExactly(carCreatedResponse.getBody());
  }

  @Test
  void testFindEmptyCarList() {

    ResponseEntity<List<Car>> carsByName = carController.findCarsByName("");
    assertThat(carsByName.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<Car> body = carsByName.getBody();
    assertThat(body).isNotNull().isEmpty();
  }

  @Test
  void testFindOneCarList() {

    Car testcar = carRepository.save(Car.builder().name("Testcar").build());
    ResponseEntity<List<Car>> carsByName = carController.findCarsByName("Testcar");
    assertThat(carsByName.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<Car> body = carsByName.getBody();
    assertThat(body).isNotNull().hasSize(1).containsExactly(testcar);
  }

  @Test
  void testFindNoCarList() {

    carRepository.save(Car.builder().name("Testcar").build());

    ResponseEntity<List<Car>> carsByName = carController.findCarsByName("NonexistingCar");
    assertThat(carsByName.getStatusCode()).isEqualTo(HttpStatus.OK);

    List<Car> body = carsByName.getBody();
    assertThat(body).isNotNull().isEmpty();
  }
}
