package com.github.benjamineckstein.showcase.skills.entity;

import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class SkillTest {

  private @Autowired
  SkillsRepository skillsRepository;

  @Test
  void testNoArgConstructor() {
    Skill testcar = new Skill();
    testcar.setName("Name");
    assertThat(testcar.getId()).isNotNull();
  }

  @Test
  void testUUIDIsGenerated() {
    Skill testcar = Skill.builder().build();
    assertThat(testcar.getId()).isNotNull();
  }

  @Test
  void testIsPersistedForNewEntity() {
    Skill testcar = Skill.builder().build();
    assertThat(testcar.isPersisted()).isFalse();
    assertThat(testcar.isNew()).isTrue();
  }

  @Test
  void testIsPersistedForOldEntity() {

    Skill skillSaved = skillsRepository.saveAndFlush(Skill.builder().build());
    assertThat(skillSaved.isPersisted()).isTrue();
    assertThat(skillSaved.isNew()).isFalse();
  }

  @Test
  void testVersionStartsAtZero() {
    Skill testcar = Skill.builder().build();
    assertThat(testcar.getVersion()).isNull();

    assertThat(skillsRepository.save(testcar).getVersion()).isEqualTo(0);
  }

  @Test
  void testVersionIncrements() {
    Skill testSkillSaved = skillsRepository.saveAndFlush(Skill.builder().build());
    testSkillSaved.setName("Test123");

    assertThat(skillsRepository.saveAndFlush(testSkillSaved).getVersion()).isGreaterThan(0);
  }
}
