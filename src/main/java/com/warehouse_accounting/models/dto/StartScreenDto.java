package com.warehouse_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class StartScreenDto {

    private Long id;

    private String startScreen;

    public String getStartScreen(StartScreenDto startScreenDto) {
        return startScreenDto.startScreen == null ? "StartScreen number: "
                + startScreenDto.id
                : startScreenDto.startScreen;
    }

    @Override
    public String toString() {
        return startScreen == null ? "Company number: " + id : startScreen;
    }
}
