package com.github.benjamineckstein.showcase.expertise.boundary;

import com.github.benjamineckstein.showcase.architecture.Boundary;
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
public class ExpertiseBoundary {

  private final ExpertiseBoundary expertiseBoundary;
}
