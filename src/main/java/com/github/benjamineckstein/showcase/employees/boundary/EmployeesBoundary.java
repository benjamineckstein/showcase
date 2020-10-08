package com.github.benjamineckstein.showcase.employees.boundary;

import com.github.benjamineckstein.showcase.architecture.Boundary;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.employees.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Boundary
@RequiredArgsConstructor
public class EmployeesBoundary {

  private final EmployeesRepository employeesRepository;

  @Transactional
  public List<Employee> findCustomerByName(String name) {
    return Optional.ofNullable(name)
        .map(
            nameNotNull ->
                    employeesRepository.findAll().stream()
                    .filter(employee -> nameNotNull.equals(employee.getName()))
                    .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

}
