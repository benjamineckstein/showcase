package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.skills.dto.SkillCreateDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoList;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
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
    assertThat(response.getHeaders().getLocation()).isNotNull().hasPath("/api/skills/TestSkill");
  }

  @Test
  void testCreateCarPersisted() {

    SkillCreateDto skillDto = SkillCreateDto.builder().name("TestSkill").build();
    ResponseEntity<SkillDto> response = skillsController.createSkill(skillDto);

    List<Skill> all = skillsRepository.findAll();
    assertThat(all).isNotEmpty().hasSize(1);
    assertThat(SkillsController.convertToDto(all.get(0))).isEqualTo(response.getBody());
  }

  @Test
  void testFindEmptyCarList() {

    ResponseEntity<SkillDtoList> carsByName = skillsController.findSkillsByName("");
    assertThat(carsByName.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(carsByName.getBody())
        .isNotNull()
        .extracting(SkillDtoList::getSkills, as(InstanceOfAssertFactories.LIST))
        .isEmpty();
  }

  @Test
  void testFindOneCarList() {

    Skill skill = skillsRepository.save(Skill.builder().name("Testcar").build());
    SkillDto skillDto =
        SkillDto.builder()
            .id(skill.getId())
            .name(skill.getName())
            .version(skill.getVersion())
            .build();

    ResponseEntity<SkillDtoList> response = skillsController.findSkillsByName("Testcar");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody())
        .isNotNull()
        .extracting(SkillDtoList::getSkills, as(InstanceOfAssertFactories.LIST))
        .hasSize(1)
        .containsExactly(skillDto);
  }

  @Test
  void testFindNoCarList() {

    skillsRepository.save(Skill.builder().name("Testcar").build());

    ResponseEntity<SkillDtoList> response = skillsController.findSkillsByName("NonexistingCar");
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody())
        .isNotNull()
        .extracting(SkillDtoList::getSkills, as(InstanceOfAssertFactories.LIST))
        .isEmpty();
  }
}
