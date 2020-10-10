package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.skills.dto.SkillCreateDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoMapper;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SkillsControllerTest {

  private @Autowired SkillsController skillsController;
  private @Autowired SkillsRepository skillsRepository;

  @Test
  void testCreateCarResponse() {

    SkillCreateDto skillDto = SkillCreateDto.builder().name("TestSkill").build();
    ResponseEntity<SkillDto> response = skillsController.createSkill(skillDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getHeaders().getLocation())
        .isNotNull()
        .matches(uri -> uri.getRawPath().contains("/api/skills/"));
  }

  @Test
  void testCreateCarPersisted() {

    SkillCreateDto skillDto = SkillCreateDto.builder().name("TestSkill").build();
    ResponseEntity<SkillDto> response = skillsController.createSkill(skillDto);

    List<Skill> all = skillsRepository.findAll();
    assertThat(all).isNotEmpty().hasSize(1);
    assertThat(SkillDtoMapper.convertToDto(all.get(0))).isEqualTo(response.getBody());
  }
}
