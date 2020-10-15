package com.github.benjamineckstein.showcase.search.repository;

import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SearchRepository extends CrudRepository<Expertise, UUID> {

  @Query(
      "Select e FROM Expertise e JOIN FETCH e.skill JOIN FETCH e.employee where "
          + "lower(e.description) like lower(CONCAT('%',:keyword,'%')) or "
          + "lower(e.employee.name) like lower(CONCAT('%',:keyword,'%')) or "
          + "lower(e.skill.name) like lower(CONCAT('%',:keyword,'%'))")
  List<Expertise> findExpertiseByKeyWord(@Param("keyword") String keyword);
}
