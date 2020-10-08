package com.github.benjamineckstein.showcase.cars.controller;

import com.github.benjamineckstein.showcase.cars.boundary.CarBoundary;
import com.github.benjamineckstein.showcase.cars.entity.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequiredArgsConstructor
public class CarController {

  private final CarBoundary carBoundary;

  @GetMapping("api/cars/{name}")
  public ResponseEntity<List<Car>> findCarsByName(@PathVariable String name) {
    return ResponseEntity.ok(carBoundary.findCarsByName(name));
  }

  @PostMapping("api/cars/{name}")
  public ResponseEntity<Car> createCar(@PathVariable String name) {
    Car carWithName = carBoundary.createCarWithName(name);

    UriComponents uriComponents =
        MvcUriComponentsBuilder.fromMethodCall(
                on(CarController.class).findCarsByName(carWithName.getName()))
            .build();

    return ResponseEntity.created(uriComponents.toUri()).body(carWithName);
  }
}