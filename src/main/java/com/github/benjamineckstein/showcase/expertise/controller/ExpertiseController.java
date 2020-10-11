package com.github.benjamineckstein.showcase.expertise.controller;

import com.github.benjamineckstein.showcase.expertise.boundary.ExpertiseBoundary;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseCreateDto;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseDto;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseDtoList;
import com.github.benjamineckstein.showcase.expertise.dto.ExpertiseDtoMapper;
import com.github.benjamineckstein.showcase.expertise.entity.Expertise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.UUID;

import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_EXPERTISE;
import static com.github.benjamineckstein.showcase.common.RoutingConstants.URL_EXPERTISE_ID;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequiredArgsConstructor
public class ExpertiseController {

  private final ExpertiseBoundary expertiseBoundary;

  @GetMapping(URL_EXPERTISE)
  public ResponseEntity<ExpertiseDtoList> getExpertiseList() {

    List<Expertise> expertises = expertiseBoundary.getExpertises();
    ExpertiseDtoList expertiseDtoList = ExpertiseDtoMapper.getExpertiseDtoList(expertises);
    return ResponseEntity.ok(expertiseDtoList);
  }

  @GetMapping(URL_EXPERTISE_ID)
  public ResponseEntity<ExpertiseDto> getExpertise(@PathVariable("Uuid") UUID expertiseId) {
    Expertise expertise = expertiseBoundary.findExpertise(expertiseId).orElseThrow();
    ExpertiseDto ExpertiseDto = ExpertiseDtoMapper.convertToDto(expertise);
    return ResponseEntity.ok(ExpertiseDto);
  }

  @PostMapping(URL_EXPERTISE)
  public ResponseEntity<ExpertiseDto> createExpertise(
      @RequestBody ExpertiseCreateDto expertiseDto) {
    Expertise expertise = expertiseBoundary.createExpertise(expertiseDto);

    UriComponents uriComponents =
        MvcUriComponentsBuilder.fromMethodCall(
                on(ExpertiseController.class).getExpertise(expertise.getId()))
            .build();

    return ResponseEntity.created(uriComponents.toUri())
        .body(ExpertiseDtoMapper.convertToDto(expertise));
  }

  @DeleteMapping(URL_EXPERTISE_ID)
  public ResponseEntity<Void> deleteSkill(@PathVariable("Uuid") UUID expertiseId) {
    expertiseBoundary.deleteExpertise(expertiseId);
    return ResponseEntity.noContent().build();
  }
}
