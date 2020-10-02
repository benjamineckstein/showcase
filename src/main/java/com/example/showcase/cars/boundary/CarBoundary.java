package com.example.showcase.cars.boundary;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.showcase.entity.Car;
import com.example.showcase.cars.repository.CarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarBoundary {
    private final CarRepository carRepository;

    @Transactional
    public List<Car> findCarsByName(String name) {
        return carRepository.findAll().stream().filter(car -> car.getName().equals(name)).collect(Collectors.toList());
    }
}
