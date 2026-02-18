package com.jordan.autocare.maintenance.mapper;

import com.jordan.autocare.maintenance.domain.Maintenance;
import com.jordan.autocare.maintenance.dto.MaintenanceResponse;

public class MaintenanceMapper {

    public static MaintenanceResponse toResponse(Maintenance maintenance) {

        return new MaintenanceResponse(
                maintenance.getId(),
                maintenance.getType(),
                maintenance.getDate(),
                maintenance.getDescription(),
                maintenance.getMileagePerformed(),
                maintenance.getNextMaintenanceMileage(),
                maintenance.calculateStatus()
        );
    }
}