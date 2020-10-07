package com.github.benjamineckstein.showcase.cars.repository;

import com.github.benjamineckstein.showcase.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
}
