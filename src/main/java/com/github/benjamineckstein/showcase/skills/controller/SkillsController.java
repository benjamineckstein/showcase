package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.common.Routing;
import com.github.benjamineckstein.showcase.skills.boundary.SkillsBoundary;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoList;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

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
                    .map(this::convertToDto)
                    .collect(Collectors.toList()))
            .build());
  }

  @PostMapping(Routing.URL_SKILLS)
  public ResponseEntity<SkillDto> createSkill(@RequestBody SkillDto skillDto) {
    Skill skillWithName = skillsBoundary.createSkill(skillDto);

    UriComponents uriComponents =
        MvcUriComponentsBuilder.fromMethodCall(
                on(SkillsController.class).findSkillsByName(skillWithName.getName()))
            .build();

    return ResponseEntity.created(uriComponents.toUri()).body(convertToDto(skillWithName));
  }

  SkillDto convertToDto(Skill skill) {

    return SkillDto.builder()
        .name(skill.getName())
        .id(skill.getId())
        .version(skill.getVersion())
        .build();
  }
}
