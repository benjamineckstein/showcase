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
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import java.util.UUID;

import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_EXPERTISE;
import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_EXPERTISE_ID;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExpertiseControllerDocumentationTest extends AbstractDocumentationTest {

  public static final ResponseFieldsSnippet responseDtoListFields =
      responseFields(
          fieldWithPath("expertise").description("List of employees"),
          fieldWithPath("expertise[].id").description("Employee UUID").type(UUID.class),
          fieldWithPath("expertise[].version").description("Entity version for optimistic locking"),
          fieldWithPath("expertise[].description").description("Expertise name"),
          fieldWithPath("expertise[].level").description("Expertise Level"),
          fieldWithPath("expertise[].skill.id").description("Skill id"),
          fieldWithPath("expertise[].skill.name").description("Skill name"),
          fieldWithPath("expertise[].skill.version").description("Skill version"),
          fieldWithPath("expertise[].employee.id").description("Employee id"),
          fieldWithPath("expertise[].employee.name").description("Employee name"),
          fieldWithPath("expertise[].employee.version").description("Employee version"));

  public static final ResponseFieldsSnippet responseDtoFields =
      responseFields(
          fieldWithPath("id").description("Employee UUID").type(UUID.class),
          fieldWithPath("version").description("Entity version for optimistic locking"),
          fieldWithPath("description").description("Expertise description"),
          fieldWithPath("level").description("Expertise Level"),
          fieldWithPath("skill.id").description("Skill id"),
          fieldWithPath("skill.name").description("Skill name"),
          fieldWithPath("skill.version").description("Skill version"),
          fieldWithPath("employee.id").description("Employee id"),
          fieldWithPath("employee.name").description("Employee name"),
          fieldWithPath("employee.version").description("Employee version"));

  @Test
  public void shouldDocumentGetExpertise() throws Exception {

    Expertise expertise = testcaseGenerator.testCase1().expertise;

    this.mockMvc
        .perform(get(URL_EXPERTISE_ID, expertise.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(expertise.getId()))))
        .andDo(document("expertiseGet", responseDtoFields));
  }

  @Test
  public void shouldDocumentGetExpertiseList() throws Exception {

    Testcase2 testcase2 = testcaseGenerator.testCase2();
    this.mockMvc
        .perform(get(URL_EXPERTISE).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(testcase2.expertise1.getId()))))
        .andDo(document("expertiseGetList", responseDtoListFields));
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
            content().string(containsString(String.valueOf(expertiseCreateDto.getDescription()))))
        .andDo(document("expertiseCreate", responseDtoFields));
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
        .andExpect(content().string(containsString(String.valueOf(expertiseUpdateDto.getLevel()))))
        .andDo(document("expertiseEdit", responseDtoFields));
  }

  @Test
  public void shouldDocumentDeleteExpertise() throws Exception {

    Expertise expertise = testcaseGenerator.testCase1().expertise;

    this.mockMvc
        .perform(delete(URL_EXPERTISE_ID, expertise.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andDo(document("expertiseDelete"));
  }
}
