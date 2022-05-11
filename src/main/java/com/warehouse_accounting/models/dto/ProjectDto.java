package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private Long id;

    private String name;

    private String code;

    private String description;

    public String getName(ProjectDto projectDto) {
        return projectDto.name == null ? "Project number: " + projectDto.id : projectDto.name;
    }

    @Override
    public String toString() {
        return name == null ? "Company number: " + id : name;
    }
}
