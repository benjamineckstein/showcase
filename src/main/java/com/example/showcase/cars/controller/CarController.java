package com.example.showcase.cars.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.showcase.cars.boundary.CarBoundary;
import com.example.showcase.cars.entity.Car;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CarController {

    final private CarBoundary carBoundary;

    @GetMapping("api/cars/{name}")
    public ResponseEntity<List<Car>> findCarsByName(@PathVariable String name){
        return ResponseEntity.ok(carBoundary.findCarsByName(name));
    }
}
