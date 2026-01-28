package org.ihor.gameengine.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MoveRequest(
    @Min(0) @Max(2) int row,
    @Min(0) @Max(2) int col,
    @NotNull @Pattern(regexp = "X|O") String player) {
}
