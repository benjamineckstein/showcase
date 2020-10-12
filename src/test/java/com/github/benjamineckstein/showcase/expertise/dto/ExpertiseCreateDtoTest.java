package com.github.benjamineckstein.showcase.expertise.dto;

import com.github.benjamineckstein.showcase.expertise.entity.ExpertiseLevel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpertiseCreateDtoTest {

  @Test
  public void allArgsConstructorShouldWorkAsIntended() {

    ExpertiseCreateDto expertiseCreateDto = new ExpertiseCreateDto();
    expertiseCreateDto.setEmployeeId(UUID.randomUUID());
    expertiseCreateDto.setSkillId(UUID.randomUUID());
    expertiseCreateDto.setDescription("Description");
    expertiseCreateDto.setLevel(ExpertiseLevel.JUNIOR);

    ExpertiseCreateDto expertiseCreateDto1 =
        new ExpertiseCreateDto(
            "Description",
            ExpertiseLevel.JUNIOR,
            expertiseCreateDto.getSkillId(),
            expertiseCreateDto.getEmployeeId());

    assertThat(expertiseCreateDto).isEqualTo(expertiseCreateDto1);
  }
}
