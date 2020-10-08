package com.github.benjamineckstein.showcase.skills.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SkillDto {

    private UUID id;
    private Integer version;
    private String name;
}
