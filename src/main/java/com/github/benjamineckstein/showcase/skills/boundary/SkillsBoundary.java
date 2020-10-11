package com.github.benjamineckstein.showcase.skills.boundary;

import com.github.benjamineckstein.showcase.architecture.Boundary;
import com.github.benjamineckstein.showcase.skills.dto.SkillCreateDto;
import com.github.benjamineckstein.showcase.skills.entity.Skill;
import com.github.benjamineckstein.showcase.skills.repository.SkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Boundary
@RequiredArgsConstructor
public class SkillsBoundary {

  private final SkillsRepository skillsRepository;

  @Transactional
  public Skill createSkill(SkillCreateDto skillDto) {
    return skillsRepository.save(Skill.builder().name(skillDto.getName()).build());
  }

  @Transactional
  public List<Skill> getSkills() {
    return skillsRepository.findAll();
  }

  @Transactional
  public void deleteSkill(UUID skillId) {
    skillsRepository.findById(skillId).ifPresent(skillsRepository::delete);
  }

  @Transactional
  public Skill updateSkill(Skill skill) {

    Assert.notNull(skill.getId(), "Skill should always have a valid ID");
    skillsRepository.findById(skill.getId()).orElseThrow();

    skill.setPersisted(true);
    return skillsRepository.save(skill);
  }

  @Transactional
  public Optional<Skill> findSkill(UUID skillId) {
    return skillsRepository.findById(skillId);
  }
}
