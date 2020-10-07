package com.example.showcase.cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.showcase.cars.entity.Car;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
}
