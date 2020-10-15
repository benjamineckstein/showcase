package com.github.benjamineckstein.showcase.employees.entity;

import com.github.benjamineckstein.showcase.employees.repository.EmployeesRepository;
import com.github.benjamineckstein.showcase.util.MySpringBootTest;
import com.github.benjamineckstein.showcase.util.Testcase1;
import com.github.benjamineckstein.showcase.util.TestcaseGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@MySpringBootTest
class EmployeeTest {

  @Autowired EmployeesRepository employeesRepository;

  @Autowired TestcaseGenerator testcaseGenerator;

  @Autowired
  EntityManager entityManager;

  @Test
  void getExpertise() {

    Testcase1 testcase1 = testcaseGenerator.testCase1();

    //we need to clear the cache / transaction to really fetch from the database again
    entityManager.flush();
    entityManager.clear();

    var employee =
        employeesRepository.findById(testcase1.expertise.getEmployee().getId()).orElseThrow();

    assertThat(employee.getExpertise()).hasSize(1).containsExactly(testcase1.expertise);
  }
}
