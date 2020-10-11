package com.github.benjamineckstein.showcase.search.controller;

import com.github.benjamineckstein.showcase.employees.dto.EmployeeDtoList;
import com.github.benjamineckstein.showcase.employees.dto.EmployeeDtoMapper;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseDtoList;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseDtoMapper;
import com.github.benjamineckstein.showcase.search.boundary.SearchBoundary;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoList;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_EMPLOYEES_FIND;
import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_EXPERTISE_SEARCH;
import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_SKILLS_FIND;

@RestController
@RequiredArgsConstructor
public class SearchController {

  private final SearchBoundary seachBoundary;

  @GetMapping(URL_SKILLS_FIND)
  public ResponseEntity<SkillDtoList> findSkillsByName(@PathVariable String name) {
    return ResponseEntity.ok(SkillDtoMapper.getSkillDtoList(seachBoundary.findSkillsByName(name)));
  }

  @GetMapping(URL_EMPLOYEES_FIND)
  public ResponseEntity<EmployeeDtoList> findEmployeesByName(@PathVariable String name) {

    return ResponseEntity.ok(
        EmployeeDtoMapper.getEmployeeDtoList(seachBoundary.findEmployeesByName(name)));
  }

  @GetMapping(URL_EXPERTISE_SEARCH)
  public ResponseEntity<ExpertiseDtoList> findExpertiseByKeyword(@PathVariable String keyword) {
    return ResponseEntity.ok(
        ExpertiseDtoMapper.getExpertiseDtoList(seachBoundary.findExpertiseByKeyword(keyword)));
  }
}
