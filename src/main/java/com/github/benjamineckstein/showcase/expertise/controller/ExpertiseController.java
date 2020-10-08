package com.github.benjamineckstein.showcase.expertise.controller;

import com.github.benjamineckstein.showcase.common.Routing;
import com.github.benjamineckstein.showcase.expertise.boundary.ExpertiseBoundary;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpertiseController {

  private final ExpertiseBoundary expertiseBoundary;

  @GetMapping(Routing.URL_EXPERTISE_SEARCH)
  public ResponseEntity<List<Expertise>> findExpertiseByName(@PathVariable String keyword) {
    return ResponseEntity.ok(null);
  }

}
