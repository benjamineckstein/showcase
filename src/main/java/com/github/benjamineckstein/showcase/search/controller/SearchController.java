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

@RestController
@RequiredArgsConstructor
public class SearchController {

    /** GET */
    public static final String URL_SEARCH_EMPLOYEES = "/api/search/employees/{name}";
    /** GET */
    public static final String URL_SEARCH_EXPERTISE = "/api/search/expertise/{keyword}";
    /** GET */
    public static final String URL_SEARCH_SKILLS = "/api/search/skills/{name}";
    private final SearchBoundary seachBoundary;

  @GetMapping(URL_SEARCH_SKILLS)
  public ResponseEntity<SkillDtoList> findSkillsByName(@PathVariable String name) {
    return ResponseEntity.ok(SkillDtoMapper.getSkillDtoList(seachBoundary.findSkillsByName(name)));
  }

  @GetMapping(URL_SEARCH_EMPLOYEES)
  public ResponseEntity<EmployeeDtoList> findEmployeesByName(@PathVariable String name) {

    return ResponseEntity.ok(
        EmployeeDtoMapper.getEmployeeDtoList(seachBoundary.findEmployeesByName(name)));
  }

  @GetMapping(URL_SEARCH_EXPERTISE)
  public ResponseEntity<ExpertiseDtoList> findExpertiseByKeyword(@PathVariable String keyword) {
    return ResponseEntity.ok(
        ExpertiseDtoMapper.getExpertiseDtoList(seachBoundary.findExpertiseByKeyword(keyword)));
  }
}
