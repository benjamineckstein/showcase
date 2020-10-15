package com.github.benjamineckstein.showcase.skills.entity;

import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import com.github.benjamineckstein.showcase.util.MySpringBootTest;
import com.github.benjamineckstein.showcase.util.Testcase1;
import com.github.benjamineckstein.showcase.util.TestcaseGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@MySpringBootTest
class SkillTest {

  @Autowired
  SkillsRepository skillsRepository;

  @Autowired TestcaseGenerator testcaseGenerator;

  @Autowired
  EntityManager entityManager;

  @Test
  void getExpertise() {

    Testcase1 testcase1 = testcaseGenerator.testCase1();

    //we need to clear the cache / transaction to really fetch from the database again
    entityManager.flush();
    entityManager.clear();

    var skill =
        skillsRepository.findById(testcase1.expertise.getSkill().getId()).orElseThrow();

    assertThat(skill.getExpertise()).hasSize(1).containsExactly(testcase1.expertise);
  }
}
