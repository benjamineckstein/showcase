package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.util.AbstractDocumentationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.github.benjamineckstein.showcase.skills.controller.SkillsController.URL_SKILLS;
import static org.hamcrest.Matchers.containsString;
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
        .andExpect(content().string(containsString(String.valueOf(skill.getId()))));
  }
}
