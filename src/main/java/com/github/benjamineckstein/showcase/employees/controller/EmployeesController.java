package com.github.benjamineckstein.showcase.employees.controller;

import com.github.benjamineckstein.showcase.common.Routing;
import com.github.benjamineckstein.showcase.employees.boundary.EmployeesBoundary;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeesController {

  private final EmployeesBoundary employeesBoundary;

  @GetMapping(Routing.URL_EMPLOYEES_FIND)
  public ResponseEntity<List<Employee>> findEmployeesByName(@PathVariable String name) {
    return ResponseEntity.ok(employeesBoundary.findCustomerByName(name));
  }

}
