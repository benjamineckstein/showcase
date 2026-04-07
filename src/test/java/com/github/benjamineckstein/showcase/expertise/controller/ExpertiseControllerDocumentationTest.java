package com.github.benjamineckstein.showcase.expertise.controller;

import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseCreateDto;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseUpdateDto;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import com.github.benjamineckstein.showcase.expertise.entity.ExpertiseLevel;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.util.AbstractDocumentationTest;
import com.github.benjamineckstein.showcase.util.Testcase2;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.benjamineckstein.showcase.expertise.controller.ExpertiseController.URL_EXPERTISE;
import static com.github.benjamineckstein.showcase.expertise.controller.ExpertiseController.URL_EXPERTISE_ID;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExpertiseControllerDocumentationTest extends AbstractDocumentationTest {

  @Test
  public void shouldDocumentGetExpertise() throws Exception {

    Expertise expertise = testcaseGenerator.testCase1().expertise;

    this.mockMvc
        .perform(get(URL_EXPERTISE_ID, expertise.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(expertise.getId()))));
  }

  @Test
  public void shouldDocumentGetExpertiseList() throws Exception {

    Testcase2 testcase2 = testcaseGenerator.testCase2();
    this.mockMvc
        .perform(get(URL_EXPERTISE).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(testcase2.expertise1.getId()))));
  }

  @Test
  public void shouldDocumentCreateExpertise() throws Exception {

    Testcase2 testcase2 = testcaseGenerator.testCase2();
    Skill skill = testcase2.expertise1.getSkill();
    Employee employee = testcase2.expertise2.getEmployee();

    ExpertiseCreateDto expertiseCreateDto =
        ExpertiseCreateDto.builder()
            .employeeId(employee.getId())
            .skillId(skill.getId())
            .description("New Expertise Description")
            .level(ExpertiseLevel.PRINCIPAL)
            .build();

    this.mockMvc
        .perform(
            post(URL_EXPERTISE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expertiseCreateDto))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(
            content().string(containsString(String.valueOf(expertiseCreateDto.getDescription()))));
  }

  @Test
  public void shouldDocumentEditExpertise() throws Exception {

    Expertise expertise = testcaseGenerator.testCase1().expertise;

    ExpertiseUpdateDto expertiseUpdateDto =
        ExpertiseUpdateDto.builder()
            .id(expertise.getId())
            .description("New Description")
            .level(ExpertiseLevel.PRINCIPAL)
            .version(expertise.getVersion())
            .build();

    this.mockMvc
        .perform(
            put(URL_EXPERTISE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expertiseUpdateDto))
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(
            content().string(containsString(String.valueOf(expertiseUpdateDto.getDescription()))))
        .andExpect(content().string(containsString(String.valueOf(expertiseUpdateDto.getLevel()))));
  }

  @Test
  public void shouldDocumentDeleteExpertise() throws Exception {

    Expertise expertise = testcaseGenerator.testCase1().expertise;

    this.mockMvc
        .perform(delete(URL_EXPERTISE_ID, expertise.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }
}
