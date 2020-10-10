package com.github.benjamineckstein.showcase.search.repository;

import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SearchRepository
    extends org.springframework.data.repository.Repository<Expertise, UUID> {

  @Query(
      "Select e FROM Expertise e JOIN FETCH e.skill JOIN FETCH e.employee where e.description like '%:keyword%' or e.employee.name like '%:keyword%' or e.skill.name like '%:keyword%'")
  List<Expertise> findExpertiseByKeyWord(@Param("keyword") String keyword);
}
