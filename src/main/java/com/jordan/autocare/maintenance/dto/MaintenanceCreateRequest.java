package com.jordan.autocare.maintenance.dto;

import com.jordan.autocare.maintenance.domain.MaintenanceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record MaintenanceCreateRequest(
        @NotNull
        MaintenanceType type,

        @NotNull
        LocalDate date,

        @NotNull
        String description,

        @NotNull
        @Positive
        Integer mileagePerformed,

        @NotNull
        @Positive
        Integer nextMaintenanceMileage
) {}
