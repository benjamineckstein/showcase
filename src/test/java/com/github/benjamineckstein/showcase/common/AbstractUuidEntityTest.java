package com.github.benjamineckstein.showcase.common;

import com.github.benjamineckstein.showcase.ShowcaseApplication;
import com.github.benjamineckstein.showcase.employees.entity.Employee;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import com.github.benjamineckstein.showcase.util.MySpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@MySpringBootTest
class AbstractUuidEntityTest {

  private @Autowired SkillsRepository skillsRepository;

  @Test
  void testNoArgConstructor() {
    Skill testSkill = new Skill();
    testSkill.setName("Name");
    assertThat(testSkill.getId()).isNotNull();
  }

  @Test
  void testNoArgConstructorEmployee() {
    Employee enitity = new Employee();
    enitity.setName("Name");
    assertThat(enitity.getId()).isNotNull();
  }

  @Test
  void testNoArgConstructorExpertise() {
    Expertise enitity = new Expertise();
    assertThat(enitity.getId()).isNotNull();
  }

  @Test
  void testUUIDIsGenerated() {
    Skill testSkill = Skill.builder().build();
    assertThat(testSkill.getId()).isNotNull();
  }


  @Test
  void testUUIDIsGeneratedBaseClass() {
    AbstractUuidEntity abstractUuidEntity = AbstractUuidEntity.builder().build();
    assertThat(abstractUuidEntity.getId()).isNotNull();
  }

  @Test
  void testUUIDIsGeneratedRandomly() {
    AbstractUuidEntity abstractUuidEntity = new AbstractUuidEntity();
    AbstractUuidEntity abstractUuidEntity2 = new AbstractUuidEntity();
    assertThat(abstractUuidEntity.getId()).isNotNull().isNotEqualTo(abstractUuidEntity2.getId());
  }

  @Test
  void testIsPersistedForNewEntity() {
    Skill testSkill = Skill.builder().build();
    assertThat(testSkill.isPersisted()).isFalse();
    assertThat(testSkill.isNew()).isTrue();
  }

  @Test
  void testIsPersistedForOldEntity() {

    Skill skillSaved = skillsRepository.saveAndFlush(Skill.builder().build());
    assertThat(skillSaved.isPersisted()).isTrue();
    assertThat(skillSaved.isNew()).isFalse();
  }

  @Test
  void testVersionStartsAtZero() {
    Skill testSkill = Skill.builder().build();
    assertThat(testSkill.getVersion()).isNull();

    assertThat(skillsRepository.save(testSkill).getVersion()).isEqualTo(0);
  }

  @Test
  void testVersionIncrements() {
    Skill testSkillSaved = skillsRepository.saveAndFlush(Skill.builder().build());
    testSkillSaved.setName("Test123");

    assertThat(skillsRepository.saveAndFlush(testSkillSaved).getVersion()).isGreaterThan(0);
  }
}
