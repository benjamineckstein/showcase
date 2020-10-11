package com.github.benjamineckstein.showcase.employees.controller;

import com.github.benjamineckstein.showcase.employees.boundary.EmployeesBoundary;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeCreateDto;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeDto;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeDtoList;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeDtoMapper;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.UUID;

import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_EMPLOYEES;
import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_EMPLOYEES_ID;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequiredArgsConstructor
public class EmployeesController {

  private final EmployeesBoundary employeesBoundary;

  @PostMapping(URL_EMPLOYEES)
  public ResponseEntity<EmployeeDto> createEmployee(
      @RequestBody EmployeeCreateDto employeeCreateDto) {
    Employee employee = employeesBoundary.createEmployee(employeeCreateDto);

    UriComponents uriComponents =
        MvcUriComponentsBuilder.fromMethodCall(
                on(EmployeesController.class).getEmployee(employee.getId()))
            .build();

    return ResponseEntity.created(uriComponents.toUri())
        .body(EmployeeDtoMapper.convertToDto(employee));
  }

  @DeleteMapping(URL_EMPLOYEES_ID)
  public ResponseEntity<Void> deleteEmployee(@PathVariable("Uuid") UUID employeeId) {
    employeesBoundary.deleteEmployee(employeeId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(URL_EMPLOYEES_ID)
  public ResponseEntity<EmployeeDto> getEmployee(@PathVariable("Uuid") UUID employeeId) {
    Employee employee = employeesBoundary.findEmployee(employeeId).orElseThrow();
    EmployeeDto EmployeeDto = EmployeeDtoMapper.convertToDto(employee);
    return ResponseEntity.ok(EmployeeDto);
  }

  @PutMapping(URL_EMPLOYEES)
  public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
    Employee employee =
        employeesBoundary.updateEmployee(EmployeeDtoMapper.convertToEntity(employeeDto));
    return ResponseEntity.ok(EmployeeDtoMapper.convertToDto(employee));
  }

  @GetMapping(URL_EMPLOYEES)
  public ResponseEntity<EmployeeDtoList> getEmployees() {

    List<Employee> employees = employeesBoundary.getEmployees();
    EmployeeDtoList EmployeeDtoList = EmployeeDtoMapper.getEmployeeDtoList(employees);
    return ResponseEntity.ok(EmployeeDtoList);
  }
}
