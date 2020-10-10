package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.common.Routing;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@ExtendWith(RestDocumentationExtension.class)
public class SkillControllerDocumentationTest {

  @Autowired SkillsRepository skillsRepository;
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .build();
  }

  @Test
  public void shouldReturnDefaultMessage() throws Exception {

    Skill testskill = skillsRepository.save(Skill.builder().name("Testskill").build());

    this.mockMvc
        .perform(get(Routing.URL_SKILLS_FIND, "Testskill").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(
            document(
                "index",
                responseFields(
                    fieldWithPath("skills").description("List of skills"),
                    fieldWithPath("skills[].id").description("Skill UUID").type(UUID.class),
                    fieldWithPath("skills[].version")
                        .description("Skill version for optimistic locking"),
                    fieldWithPath("skills[].name").description("Skill name"))));
  }
}
