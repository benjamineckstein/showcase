package com.github.benjamineckstein.showcase.expertise.dto;

import com.github.benjamineckstein.showcase.expertise.entity.ExpertiseLevel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpertiseUpdateDtoTest {

  @Test
  public void shouldCancelEditNonExistingExpertise() throws Exception {

    ExpertiseUpdateDto expertiseUpdateDto = new ExpertiseUpdateDto();
    expertiseUpdateDto.setId(UUID.randomUUID());
    expertiseUpdateDto.setDescription("Description");
    expertiseUpdateDto.setLevel(ExpertiseLevel.DISTINGUISHED);
    expertiseUpdateDto.setVersion(15);

    ExpertiseUpdateDto expertiseUpdateDto1 =
        new ExpertiseUpdateDto(expertiseUpdateDto.getId(), "Description", ExpertiseLevel.DISTINGUISHED, 15);

    assertThat(expertiseUpdateDto).isEqualTo(expertiseUpdateDto1);
  }
}
