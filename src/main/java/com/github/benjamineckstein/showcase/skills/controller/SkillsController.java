package com.github.benjamineckstein.showcase.skills.controller;

import com.github.benjamineckstein.showcase.skills.boundary.SkillsBoundary;
import com.github.benjamineckstein.showcase.skills.dto.SkillCreateDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoList;
import com.github.benjamineckstein.showcase.skills.dto.SkillDtoMapper;
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

import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_SKILLS;
import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_SKILLS_ID;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequiredArgsConstructor
public class SkillsController {

  private final SkillsBoundary skillsBoundary;

  @PostMapping(URL_SKILLS)
  public ResponseEntity<SkillDto> createSkill(@RequestBody SkillCreateDto skillDto) {
    Skill skill = skillsBoundary.createSkill(skillDto);

    UriComponents uriComponents =
        MvcUriComponentsBuilder.fromMethodCall(on(SkillsController.class).getSkill(skill.getId()))
            .build();

    return ResponseEntity.created(uriComponents.toUri()).body(SkillDtoMapper.convertToDto(skill));
  }

  @DeleteMapping(URL_SKILLS_ID)
  public ResponseEntity<Void> deleteSkill(@PathVariable("Uuid") UUID skillId) {
    skillsBoundary.deleteSkill(skillId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping(URL_SKILLS_ID)
  public ResponseEntity<SkillDto> getSkill(@PathVariable("Uuid") UUID skillId) {
    Skill skill = skillsBoundary.findSkill(skillId).orElseThrow();
    SkillDto skillDto = SkillDtoMapper.convertToDto(skill);
    return ResponseEntity.ok(skillDto);
  }

  @PutMapping(URL_SKILLS)
  public ResponseEntity<SkillDto> updateSkill(@RequestBody SkillDto skillDto) {
    Skill skill = skillsBoundary.updateSkill(SkillDtoMapper.convertToEntity(skillDto));
    return ResponseEntity.ok(SkillDtoMapper.convertToDto(skill));
  }

  @GetMapping(URL_SKILLS)
  public ResponseEntity<SkillDtoList> getSkills() {

    List<Skill> skills = skillsBoundary.getSkills();
    SkillDtoList skillDtoList = SkillDtoMapper.getSkillDtoList(skills);
    return ResponseEntity.ok(skillDtoList);
  }
}
