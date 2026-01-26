package com.jordan.autocare.maintenance.dto;

import com.jordan.autocare.maintenance.domain.MaintenanceStatus;
import com.jordan.autocare.maintenance.domain.MaintenanceType;

import java.time.LocalDate;

public record MaintenanceResponse(Long id,
                                  MaintenanceType type,
                                  LocalDate date,
                                  Integer mileagePerformed,
                                  Integer nextMaintenanceMileage,
                                  MaintenanceStatus status) {
}
