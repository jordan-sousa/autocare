package com.jordan.autocare.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VehicleCreateRequest(@NotBlank String brand,
                                   @NotBlank String model,
                                   @NotNull Integer year,
                                   @NotNull @Positive Integer currentMileage) {
}
