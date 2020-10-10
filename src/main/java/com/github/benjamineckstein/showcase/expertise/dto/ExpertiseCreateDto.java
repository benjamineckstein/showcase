package com.github.benjamineckstein.showcase.expertise.dto;

import com.github.benjamineckstein.showcase.employees.dto.EmployeeDto;
import com.github.benjamineckstein.showcase.skills.dto.SkillDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ExpertiseCreateDto {

    private String description;

    private UUID skillId;
    private UUID employeeId;
}
