package com.github.benjamineckstein.showcase.employees.boundary;

import com.github.benjamineckstein.showcase.architecture.Boundary;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeCreateDto;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.employees.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Boundary
@RequiredArgsConstructor
public class EmployeesBoundary {

  private final EmployeesRepository employeesRepository;

  @Transactional
  public Employee createEmployee(EmployeeCreateDto employeeDto) {
    return employeesRepository.save(Employee.builder().name(employeeDto.getName()).build());
  }

  @Transactional
  public List<Employee> getEmployees() {
    return employeesRepository.findAll();
  }

  @Transactional
  public void deleteEmployee(UUID employeeId) {
    employeesRepository.findById(employeeId).ifPresent(employeesRepository::delete);
  }

  @Transactional
  public Employee updateEmployee(Employee employee) {
    employee.setPersisted(true);
    return employeesRepository.save(employee);
  }

  @Transactional
  public Optional<Employee> findEmployee(UUID employeeId) {
    return employeesRepository.findById(employeeId);
  }
}
