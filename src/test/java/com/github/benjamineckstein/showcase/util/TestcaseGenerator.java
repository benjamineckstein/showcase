package com.github.benjamineckstein.showcase.util;

import com.github.benjamineckstein.showcase.employees.dto.EmployeeDto;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.employees.repository.EmployeesRepository;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import com.github.benjamineckstein.showcase.expertise.entity.ExpertiseLevel;
import com.github.benjamineckstein.showcase.expertise.repository.ExpertiseRepository;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TestcaseGenerator {

  @Autowired private SkillsRepository skillsRepository;
  @Autowired private EmployeesRepository employeesRepository;
  @Autowired private ExpertiseRepository expertiseRepository;

  public Testcase1 testCase1() {

    Expertise expertise = expertiseFixture();
    expertise.setSkill(skillsRepository.saveAndFlush(expertise.getSkill()));
    expertise.setEmployee(employeesRepository.saveAndFlush(expertise.getEmployee()));

    expertiseRepository.saveAndFlush(expertise);
    return new Testcase1(expertise);
  }

  public Testcase2 testCase2() {

    Expertise expertise = expertiseFixture();
    expertise.setSkill(skillsRepository.saveAndFlush(expertise.getSkill()));
    expertise.setEmployee(employeesRepository.saveAndFlush(expertise.getEmployee()));
    expertiseRepository.saveAndFlush(expertise);

    Expertise expertise2 = expertiseFixture();
    expertise2.setSkill(skillsRepository.saveAndFlush(expertise2.getSkill()));
    expertise2.setEmployee(employeesRepository.saveAndFlush(expertise2.getEmployee()));

    return new Testcase2(expertise, expertise2);
  }

  public static Skill skillFixture() {
    return Skill.builder().name("Testskill").build();
  }

  public static Employee employeeFixture() {
    return Employee.builder().name("Testemployee").build();
  }

  public static EmployeeDto employeeDtoFixture() {
    return EmployeeDto.builder().id(UUID.randomUUID()).name("TestDtoEmployee").version(0).build();
  }

  public static Expertise expertiseFixture() {
    return Expertise.builder()
        .description("TestExpertise")
        .level(ExpertiseLevel.JUNIOR)
        .skill(skillFixture())
        .employee(employeeFixture())
        .build();
  }
}
