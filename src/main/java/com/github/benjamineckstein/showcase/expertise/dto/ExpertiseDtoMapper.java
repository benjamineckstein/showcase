package com.github.benjamineckstein.showcase.expertise.dto;

import com.github.benjamineckstein.showcase.employees.dto.EmployeeDtoMapper;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ExpertiseDtoMapper {
  public static ExpertiseDtoList getExpertiseDtoList(List<Expertise> expertises) {
    return ExpertiseDtoList.builder()
        .expertise(
            expertises.stream().map(ExpertiseDtoMapper::convertToDto).collect(Collectors.toList()))
        .build();
  }

  public static ExpertiseDto convertToDto(Expertise expertise) {
    return ExpertiseDto.builder()
        .description(expertise.getDescription())
        .id(expertise.getId())
        .version(expertise.getVersion())
        .employee(EmployeeDtoMapper.convertToDto(expertise.getEmployee()))
        .skill(SkillDtoMapper.convertToDto(expertise.getSkill()))
        .build();
  }

  public static Expertise convertToEntity(ExpertiseDto expertiseDto) {
    return Expertise.builder()
        .description(expertiseDto.getDescription())
        .id(expertiseDto.getId())
        .version(expertiseDto.getVersion())
        .persisted(expertiseDto.getVersion() >= 0)
        .build();
  }
}
