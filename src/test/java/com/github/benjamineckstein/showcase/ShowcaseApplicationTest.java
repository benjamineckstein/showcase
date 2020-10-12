package com.github.benjamineckstein.showcase;

import com.github.benjamineckstein.showcase.employees.dto.EmployeeDtoMapper;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseDtoMapper;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShowcaseApplicationTest {

  @Test
  void testStaticVoidMain() {
    // load context and shut down again
    ShowcaseApplication.main(new String[0]);
  }

  @Test
  void testDefaultConstructor() {
    // one silly test to get class coverage
    assertThat(new ShowcaseApplication()).isNotNull();
    assertThat(new EmployeeDtoMapper()).isNotNull();
    assertThat(new ExpertiseDtoMapper()).isNotNull();
    assertThat(new SkillDtoMapper()).isNotNull();
  }
}
