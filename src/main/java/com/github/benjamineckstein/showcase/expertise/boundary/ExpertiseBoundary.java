package com.github.benjamineckstein.showcase.expertise.boundary;

import com.github.benjamineckstein.showcase.architecture.Boundary;
import com.github.benjamineckstein.showcase.employees.boundary.EmployeesBoundary;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseCreateDto;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import com.github.benjamineckstein.showcase.expertise.entity.ExpertiseLevel;
import com.github.benjamineckstein.showcase.expertise.repository.ExpertiseRepository;
import com.github.benjamineckstein.showcase.skills.boundary.SkillsBoundary;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Boundary
@RequiredArgsConstructor
public class ExpertiseBoundary {

  private final ExpertiseRepository expertiseRepository;
  private final SkillsBoundary skillsBoundary;
  private final EmployeesBoundary employeesBoundary;

  @Transactional
  public Expertise createExpertise(ExpertiseCreateDto expertiseDto) {

    Expertise expertise =
        Expertise.builder()
            .description(expertiseDto.getDescription())
            .level(expertiseDto.getLevel())
            .employee(employeesBoundary.findEmployee(expertiseDto.getEmployeeId()).orElseThrow())
            .skill(skillsBoundary.findSkill(expertiseDto.getSkillId()).orElseThrow())
            .build();
    return expertiseRepository.save(expertise);
  }

  @Transactional
  public Optional<Expertise> findExpertise(UUID expertiseId) {
    return expertiseRepository.findByIdAndFetch(expertiseId);
  }

  @Transactional
  public void deleteExpertise(UUID expertiseId) {
    expertiseRepository.findById(expertiseId).ifPresent(expertiseRepository::delete);
  }

  @Transactional
  public List<Expertise> getExpertises() {
    return expertiseRepository.findAllAndFetch();
  }

  @Transactional
  public Expertise updateExpertise(UUID id, String description, ExpertiseLevel level, int version) {
    Expertise expertise = expertiseRepository.findById(id).orElseThrow();
    expertise.setVersion(version);
    expertise.setLevel(level);
    expertise.setDescription(description);
    return expertiseRepository.save(expertise);
  }
}
