package com.github.benjamineckstein.showcase.employees.controller;

import com.github.benjamineckstein.showcase.employees.dto.EmployeeCreateDto;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeDto;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeDtoMapper;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.util.AbstractDocumentationTest;
import com.github.benjamineckstein.showcase.util.Testcase2;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.benjamineckstein.showcase.employees.controller.EmployeesController.URL_EMPLOYEES;
import static com.github.benjamineckstein.showcase.employees.controller.EmployeesController.URL_EMPLOYEES_ID;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EmployeeControllerDocumentationTest extends AbstractDocumentationTest {

  @Test
  public void shouldDocumentGetEmployee() throws Exception {

    Employee employee = testcaseGenerator.testCase1().expertise.getEmployee();

    this.mockMvc
        .perform(get(URL_EMPLOYEES_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(employee.getId()))));
  }

  @Test
  public void shouldDocumentGetEmployees() throws Exception {

    Testcase2 testcase2 = testcaseGenerator.testCase2();
    this.mockMvc
        .perform(get(URL_EMPLOYEES).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(containsString(String.valueOf(testcase2.expertise1.getEmployee().getId()))));
  }

  @Test
  public void shouldDocumentCreateEmployee() throws Exception {

    EmployeeCreateDto employeeCreateDto = EmployeeCreateDto.builder().name("Testemployee").build();
    this.mockMvc
        .perform(
            post(URL_EMPLOYEES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeeCreateDto))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().string(containsString(String.valueOf(employeeCreateDto.getName()))));
  }

  @Test
  public void shouldDocumentEditEmployee() throws Exception {

    EmployeeDto employeeDto =
        EmployeeDtoMapper.convertToDto(testcaseGenerator.testCase1().expertise.getEmployee());

    employeeDto.setName("NewEditedEmployeeName");

    this.mockMvc
        .perform(
            put(URL_EMPLOYEES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employeeDto))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(employeeDto.getName()))));
  }

  @Test
  public void shouldDocumentDeleteEmployee() throws Exception {

    Employee employee = testcaseGenerator.testCase1().expertise.getEmployee();

    this.mockMvc
        .perform(delete(URL_EMPLOYEES_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }
}
