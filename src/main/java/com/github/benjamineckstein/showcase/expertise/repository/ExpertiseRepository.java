package com.github.benjamineckstein.showcase.expertise.repository;

import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, UUID> {}
