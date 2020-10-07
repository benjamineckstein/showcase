package com.example.showcase.cars.boundary;

import com.example.showcase.architecture.Boundary;
import com.example.showcase.cars.entity.Car;
import com.example.showcase.cars.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Boundary
@RequiredArgsConstructor
public class CarBoundary {
    private final CarRepository carRepository;

    @Transactional
    public List<Car> findCarsByName(String name) {
        return Optional.ofNullable(name)
                .map(nameNotNull ->
                        carRepository
                                .findAll()
                                .stream()
                                .filter(car -> nameNotNull.equals(car.getName())).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    @Transactional
    public Car createCarWithName(String name) {
        return carRepository.save(Car.builder().name(name).build());
    }

}
