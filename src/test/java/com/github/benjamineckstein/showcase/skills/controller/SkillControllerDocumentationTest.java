package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.util.AbstractDocumentationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_SKILLS;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SkillControllerDocumentationTest extends AbstractDocumentationTest {

  @Test
  public void shouldDocumentGetSkills() throws Exception {

    Skill skill = testcaseGenerator.testCase1().expertise.getSkill();

    this.mockMvc
        .perform(get(URL_SKILLS, skill.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString(String.valueOf(skill.getId()))))
        .andDo(
            document(
                "skillsGetList",
                responseFields(
                    fieldWithPath("skills").description("List of skills"),
                    fieldWithPath("skills[].id").description("Skill UUID").type(UUID.class),
                    fieldWithPath("skills[].version")
                        .description("Skill version for optimistic locking"),
                    fieldWithPath("skills[].name").description("Skill name"))));
  }
}
