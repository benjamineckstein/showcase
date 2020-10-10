package com.github.benjamineckstein.showcase.employees.dto;

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
public class EmployeeDtoList {

    @Builder.Default
    private List<EmployeeDto> skills = Collections.emptyList();
}
