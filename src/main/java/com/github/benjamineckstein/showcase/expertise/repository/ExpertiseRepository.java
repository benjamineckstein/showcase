package com.github.benjamineckstein.showcase.expertise.repository;

import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpertiseRepository extends JpaRepository<Expertise, UUID> {

  @Query("SELECT e from Expertise e join fetch e.skill join fetch e.employee")
  List<Expertise> findAllAndFetch();

  @Query("SELECT e from Expertise e join fetch e.skill join fetch e.employee where e.id = :uuid")
  Optional<Expertise> findByIdAndFetch(@Param("uuid") UUID uuid);
}
