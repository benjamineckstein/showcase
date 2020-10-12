package com.github.benjamineckstein.showcase.employees.dto;

import com.github.benjamineckstein.showcase.employees.entity.Employee;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDtoMapper {
  public static EmployeeDtoList getEmployeeDtoList(List<Employee> employees) {
    return EmployeeDtoList.builder()
        .employees(
            employees.stream().map(EmployeeDtoMapper::convertToDto).collect(Collectors.toList()))
        .build();
  }

  public static EmployeeDto convertToDto(Employee employee) {
    return EmployeeDto.builder()
        .name(employee.getName())
        .id(employee.getId())
        .version(employee.getVersion())
        .build();
  }

  public static Employee convertToEntity(EmployeeDto employeeDto) {
    return Employee.builder()
        .name(employeeDto.getName())
        .id(employeeDto.getId())
        .version(employeeDto.getVersion())
        //.persisted(employeeDto.getVersion() >= 0)
        .build();
  }
}
