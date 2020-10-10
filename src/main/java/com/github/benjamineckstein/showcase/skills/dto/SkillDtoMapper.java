package com.github.benjamineckstein.showcase.skills.dto;

import com.github.benjamineckstein.showcase.skills.entity.Skill;

import java.util.List;
import java.util.stream.Collectors;

public class SkillDtoMapper {
  public static SkillDtoList getSkillDtoList(List<Skill> skills) {
    return SkillDtoList.builder()
        .skills(skills.stream().map(SkillDtoMapper::convertToDto).collect(Collectors.toList()))
        .build();
  }

  public static SkillDto convertToDto(Skill skill) {
    return SkillDto.builder()
        .name(skill.getName())
        .id(skill.getId())
        .version(skill.getVersion())
        .build();
  }

  public static Skill convertToEntity(SkillDto skillDto) {
    return Skill.builder()
        .name(skillDto.getName())
        .id(skillDto.getId())
        .version(skillDto.getVersion())
        .persisted(skillDto.getVersion() >= 0)
        .build();
  }
}
