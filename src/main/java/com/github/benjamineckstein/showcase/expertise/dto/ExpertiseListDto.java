package com.github.benjamineckstein.showcase.expertise.dto;

import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExpertiseListDto {

    @Builder.Default
    private List<ExpertiseDto> expertise = Collections.emptyList();
}
