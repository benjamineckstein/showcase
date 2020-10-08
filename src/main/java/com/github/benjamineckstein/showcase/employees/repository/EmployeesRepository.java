package com.github.benjamineckstein.showcase.employees.repository;

import com.github.benjamineckstein.showcase.employees.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeesRepository extends JpaRepository<Employee, UUID> {}
