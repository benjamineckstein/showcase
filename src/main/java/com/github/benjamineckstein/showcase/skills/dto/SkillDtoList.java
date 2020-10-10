package com.github.benjamineckstein.showcase.skills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SkillDtoList {

  @Builder.Default private List<SkillDto> skills = Collections.emptyList();
}
