package com.jordan.autocare.vehicle.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VehicleMileageUpdateRequest(@NotNull @Positive Integer newMileage) {
}
