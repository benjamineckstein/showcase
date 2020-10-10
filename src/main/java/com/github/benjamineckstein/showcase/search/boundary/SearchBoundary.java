package com.github.benjamineckstein.showcase.search.boundary;

import com.github.benjamineckstein.showcase.architecture.Boundary;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.employees.repository.EmployeesRepository;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import com.github.benjamineckstein.showcase.search.repository.SearchRepository;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Boundary
@RequiredArgsConstructor
public class SearchBoundary {

  private final EmployeesRepository employeesRepository;
  private final SearchRepository searchRepository;
  private final SkillsRepository skillsRepository;

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

  @Transactional
  public List<Skill> findSkillsByName(String name) {
    return Optional.ofNullable(name)
        .map(
            nameNotNull ->
                skillsRepository.findAll().stream()
                    .filter(skill -> nameNotNull.equals(skill.getName()))
                    .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  @Transactional
  public List<Expertise> findExpertiseByKeyword(String keyword) {
    return searchRepository.findExpertiseByKeyWord(keyword);
  }
}
