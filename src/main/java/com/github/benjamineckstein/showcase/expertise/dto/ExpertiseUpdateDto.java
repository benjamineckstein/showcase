package com.github.benjamineckstein.showcase.expertise.dto;

import com.github.benjamineckstein.showcase.expertise.entity.ExpertiseLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExpertiseUpdateDto {

  private UUID id;
  private String description;
  private ExpertiseLevel level;
  private int version;
}
