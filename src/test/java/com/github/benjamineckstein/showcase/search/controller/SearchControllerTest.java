package com.github.benjamineckstein.showcase.search.controller;

import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoList;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import com.github.benjamineckstein.showcase.util.MySpringBootTest;
import com.github.benjamineckstein.showcase.util.Testcase1;
import com.github.benjamineckstein.showcase.util.TestcaseGenerator;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@MySpringBootTest
class SearchControllerTest {

  private @Autowired SearchController searchController;
  private @Autowired SkillsRepository skillsRepository;
  private @Autowired TestcaseGenerator testcaseGenerator;

  @Test
  void testFindEmptySkillSearchList() {

    ResponseEntity<SkillDtoList> skillsByName = searchController.findSkillsByName("");
    assertThat(skillsByName.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(skillsByName.getBody())
        .isNotNull()
        .extracting(SkillDtoList::getSkills, as(InstanceOfAssertFactories.LIST))
        .isEmpty();
  }

  @Test
  void testFindOneSkillList() {

    Testcase1 testcase1 = testcaseGenerator.testCase1();
    Skill skill = testcase1.expertise.getSkill();
    SkillDto skillDto =
        SkillDto.builder()
            .id(skill.getId())
            .name(skill.getName())
            .version(skill.getVersion())
            .build();

    ResponseEntity<SkillDtoList> response = searchController.findSkillsByName(skill.getName());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody())
        .isNotNull()
        .extracting(SkillDtoList::getSkills, as(InstanceOfAssertFactories.LIST))
        .hasSize(1)
        .containsExactly(skillDto);
  }

  @Test
  void testFindNoSkillList() {

    testcaseGenerator.testCase1();

    ResponseEntity<SkillDtoList> response = searchController.findSkillsByName("NonexistingSkill");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody())
        .isNotNull()
        .extracting(SkillDtoList::getSkills, as(InstanceOfAssertFactories.LIST))
        .isEmpty();
  }
}
