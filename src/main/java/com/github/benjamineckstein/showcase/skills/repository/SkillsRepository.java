package com.github.benjamineckstein.showcase.skills.repository;

import com.github.benjamineckstein.showcase.skills.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SkillsRepository extends JpaRepository<Skill, UUID> {}
