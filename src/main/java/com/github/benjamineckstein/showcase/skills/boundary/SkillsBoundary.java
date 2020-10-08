package com.github.benjamineckstein.showcase.skills.boundary;

import com.github.benjamineckstein.showcase.architecture.Boundary;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Boundary
@RequiredArgsConstructor
public class SkillsBoundary {

  private final SkillsRepository skillsRepository;

  @Transactional
  public List<Skill> findSkillsByName(String name) {
    return Optional.ofNullable(name)
        .map(
            nameNotNull ->
                skillsRepository.findAll().stream()
                    .filter(skill -> nameNotNull.equals(skill.getName()))
                    .collect(Collectors.toList()))
        .orElse(new ArrayList<>());
  }

  @Transactional
  public Skill createSkill(SkillDto skillDto) {
    return skillsRepository.save(Skill.builder().name(skillDto.getName()).build());
  }
}
