package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.common.Routing;
import com.github.benjamineckstein.showcase.skills.boundary.SkillsBoundary;
import com.github.benjamineckstein.showcase.skills.dto.SkillCreateDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoList;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequiredArgsConstructor
public class SkillsController {

  private final SkillsBoundary skillsBoundary;

  @GetMapping(Routing.URL_SKILLS_FIND)
  public ResponseEntity<SkillDtoList> findSkillsByName(@PathVariable String name) {
    return ResponseEntity.ok(
        SkillDtoList.builder()
            .skills(
                skillsBoundary.findSkillsByName(name).stream()
                    .map(SkillsController::convertToDto)
                    .collect(Collectors.toList()))
            .build());
  }

  @PostMapping(Routing.URL_SKILLS)
  public ResponseEntity<SkillDto> createSkill(@RequestBody SkillCreateDto skillDto) {
    Skill skillWithName = skillsBoundary.createSkill(skillDto);

    UriComponents uriComponents =
        MvcUriComponentsBuilder.fromMethodCall(
                on(SkillsController.class).findSkillsByName(skillWithName.getName()))
            .build();

    return ResponseEntity.created(uriComponents.toUri()).body(convertToDto(skillWithName));
  }

  @DeleteMapping(Routing.URL_SKILLS_ID)
  public ResponseEntity<Void> deleteSkill(@PathVariable("Uuid") UUID skillId) {
    skillsBoundary.deleteSkill(skillId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(Routing.URL_SKILLS_ID)
  public ResponseEntity<SkillDto> getSkill(@PathVariable("Uuid") UUID skillId) {
    Skill skill = skillsBoundary.findSkill(skillId).orElseThrow();
    SkillDto skillDto = convertToDto(skill);
    return ResponseEntity.ok(skillDto);
  }

  @PutMapping(Routing.URL_SKILLS)
  public ResponseEntity<SkillDto> updateSkill(@RequestBody SkillDto skillDto) {
    Skill skill = skillsBoundary.updateSkill(convertToEntity(skillDto));
    return ResponseEntity.ok(convertToDto(skill));
  }

  @GetMapping(Routing.URL_SKILLS)
  public ResponseEntity<SkillDtoList> getSkills() {

    List<Skill> skills = skillsBoundary.getSkills();
    SkillDtoList skillDtoList =
        SkillDtoList.builder()
            .skills(
                skills.stream().map(SkillsController::convertToDto).collect(Collectors.toList()))
            .build();
    return ResponseEntity.ok(skillDtoList);
  }

  static SkillDto convertToDto(Skill skill) {
    return SkillDto.builder()
        .name(skill.getName())
        .id(skill.getId())
        .version(skill.getVersion())
        .build();
  }

  static Skill convertToEntity(SkillDto skillDto) {
    return Skill.builder()
        .name(skillDto.getName())
        .id(skillDto.getId())
        .version(skillDto.getVersion())
        .persisted(skillDto.getVersion() >= 0)
        .build();
  }
}
