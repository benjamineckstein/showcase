package com.github.benjamineckstein.showcase.search.controller;

import com.github.benjamineckstein.showcase.employees.controller.EmployeeControllerDocumentationTest;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.expertise.controller.ExpertiseControllerDocumentationTest;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import com.github.benjamineckstein.showcase.skills.controller.SkillControllerDocumentationTest;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.util.AbstractDocumentationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.benjamineckstein.showcase.search.controller.SearchController.URL_SEARCH_EMPLOYEES;
import static com.github.benjamineckstein.showcase.search.controller.SearchController.URL_SEARCH_EXPERTISE;
import static com.github.benjamineckstein.showcase.search.controller.SearchController.URL_SEARCH_SKILLS;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchControllerDocumentationTest extends AbstractDocumentationTest {

  @Test
  public void shouldDocumentSearchEmployees() throws Exception {

    Employee employee = testcaseGenerator.testCase1().expertise.getEmployee();

    this.mockMvc
        .perform(get(URL_SEARCH_EMPLOYEES, employee.getName()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(employee.getId()))))
        .andDo(
            document(
                "searchEmployeesList", EmployeeControllerDocumentationTest.responseDtoListFields));
  }

  @Test
  public void shouldDocumentSearchSkills() throws Exception {

    Skill skill = testcaseGenerator.testCase1().expertise.getSkill();

    this.mockMvc
        .perform(get(URL_SEARCH_SKILLS, skill.getName()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(skill.getId()))))
        .andDo(
            document("searchSkillsList", SkillControllerDocumentationTest.responseDtoListFields));
  }

  @Test
  public void shouldDocumentSearchExpertise() throws Exception {

    Expertise expertise = testcaseGenerator.testCase1().expertise;

    this.mockMvc
        .perform(
            get(URL_SEARCH_EXPERTISE, expertise.getDescription())
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(expertise.getId()))))
        .andDo(
            document(
                "searchEmployeesList", ExpertiseControllerDocumentationTest.responseDtoListFields));
  }
}
