package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.skills.dto.SkillCreateDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoList;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoMapper;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import com.github.benjamineckstein.showcase.util.MySpringBootTest;
import com.github.benjamineckstein.showcase.util.TestcaseGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.NoSuchElementException;
import java.util.UUID;

import static com.github.benjamineckstein.showcase.util.TestHelper.getBody;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@MySpringBootTest
class SkillsControllerTest {

  private @Autowired SkillsController skillsController;
  private @Autowired SkillsRepository skillsRepository;
  private @Autowired TestcaseGenerator testcaseGenerator;

  @Test
  void testCreateSkillResponse() {

    SkillCreateDto skillDto = new SkillCreateDto();
    skillDto.setName("TestSkill");
    ResponseEntity<SkillDto> response = skillsController.createSkill(skillDto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    String expectedUrl = "/api/skills/" + getBody(response).getId().toString();

    assertThat(response.getHeaders().getLocation())
        .isNotNull()
        .matches(uri -> uri.getRawPath().contains(expectedUrl));
  }

  @Test
  void testCreateSkillPersisted() {

    SkillCreateDto skillCreateDto = new SkillCreateDto("TestSkill");
    ResponseEntity<SkillDto> response = skillsController.createSkill(skillCreateDto);

    Skill skill = skillsRepository.findById(getBody(response).getId()).orElseThrow();
    assertThat(skill.getName()).isEqualTo(skillCreateDto.getName());
    assertThat(SkillDtoMapper.convertToDto(skill)).isEqualTo(getBody(response));
  }

  @Test
  void testCreateSkillEmpty() {

    SkillCreateDto skillCreateDto = SkillCreateDto.builder().build();
    ResponseEntity<SkillDto> response = skillsController.createSkill(skillCreateDto);

    Skill skill = skillsRepository.findById(getBody(response).getId()).orElseThrow();
    assertThat(skill.getName()).isEqualTo(skillCreateDto.getName());
    assertThat(SkillDtoMapper.convertToDto(skill)).isEqualTo(getBody(response));
  }

  @Test
  void testGetSkillExisting() {

    Skill skill = testcaseGenerator.testCase1().expertise.getSkill();
    ResponseEntity<SkillDto> response = skillsController.getSkill(skill.getId());

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    SkillDto body = getBody(response);
    assertThat(body).isNotNull().isEqualTo(SkillDtoMapper.convertToDto(skill));
  }

  @Test
  void testGetSkillNonExisting() {
    assertThatThrownBy(() -> skillsController.getSkill(UUID.randomUUID()))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void testupdateSkillExisting() {

    Skill skill = testcaseGenerator.testCase1().expertise.getSkill();
    SkillDto dto = SkillDtoMapper.convertToDto(skill);
    dto.setName("NewSkillName");
    ResponseEntity<SkillDto> response = skillsController.updateSkill(dto);

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    SkillDto body = getBody(response);
    assertThat(body).isNotNull().isEqualTo(SkillDtoMapper.convertToDto(skill));

    assertThat(skillsRepository.findById(skill.getId()).orElseThrow().getName())
        .isEqualTo(dto.getName());
  }

  @Test
  void testUpdateSkillNonExisting() {
    assertThatThrownBy(
            () -> skillsController.updateSkill(SkillDto.builder().id(UUID.randomUUID()).build()))
        .isInstanceOf(NoSuchElementException.class);
  }

  @Test
  void testUpdateSkillWrongVersion() {

    Skill skill = testcaseGenerator.testCase1().expertise.getSkill();
    SkillDto dto = SkillDtoMapper.convertToDto(skill);
    dto.setVersion(2);
    assertThatThrownBy(() -> skillsController.updateSkill(dto))
        .isInstanceOf(ObjectOptimisticLockingFailureException.class);
  }

  @Test
  void testDeleteSkillExisting() {

    Skill skill = testcaseGenerator.testCase1().expertise.getSkill();
    ResponseEntity<Void> response = skillsController.deleteSkill(skill.getId());

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    assertThat(skillsRepository.findById(skill.getId())).isEmpty();
  }

  @Test
  void testDeleteNonExisting() {

    ResponseEntity<Void> response = skillsController.deleteSkill(UUID.randomUUID());

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void testGetSkillsExisting() {

    Skill skill = testcaseGenerator.testCase1().expertise.getSkill();
    ResponseEntity<SkillDtoList> response = skillsController.getSkills();

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    SkillDtoList body = getBody(response);
    assertThat(body.getSkills())
        .isNotNull()
        .hasSize(1)
        .containsExactly(SkillDtoMapper.convertToDto(skill));
  }

  @Test
  void testGetNonExisting() {

    ResponseEntity<SkillDtoList> response = skillsController.getSkills();

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    SkillDtoList body = getBody(response);
    assertThat(body.getSkills()).isNotNull().isEmpty();
  }
}
