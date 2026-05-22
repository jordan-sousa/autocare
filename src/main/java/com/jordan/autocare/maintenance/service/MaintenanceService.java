package com.jordan.autocare.maintenance.service;

import com.jordan.autocare.maintenance.domain.Maintenance;
import com.jordan.autocare.maintenance.dto.MaintenanceCreateRequest;
import com.jordan.autocare.maintenance.dto.MaintenanceResponse;
import com.jordan.autocare.maintenance.exception.MaintenanceNotFoundException;
import com.jordan.autocare.maintenance.exception.MaintenanceNotOwnedException;
import com.jordan.autocare.maintenance.mapper.MaintenanceMapper;
import com.jordan.autocare.maintenance.repository.MaintenanceRepository;
import com.jordan.autocare.vehicle.domain.Vehicle;
import com.jordan.autocare.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleService vehicleService;

    @Transactional
    public MaintenanceResponse registerMaintenance(Long vehicleId, MaintenanceCreateRequest request) {

        Vehicle vehicle = vehicleService.findEntityById(vehicleId);

        vehicle.validateMaintenanceMileage(request.mileagePerformed());

        Maintenance maintenance = Maintenance.builder()
                .type(request.type())
                .date(request.date())
                .description(request.description())
                .mileagePerformed(request.mileagePerformed())
                .nextMaintenanceMileage(request.nextMaintenanceMileage())
                .vehicle(vehicle)
                .build();

        maintenance.validateMaintenanceMileage();

        Maintenance saved = maintenanceRepository.save(maintenance);

        return MaintenanceMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public MaintenanceResponse findById(Long vehicleId, Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findByIdAndVehicleId(maintenanceId, vehicleId)
                .orElseThrow(() -> new MaintenanceNotFoundException(maintenanceId));

        if (!maintenance.getVehicle().getId().equals(vehicleId)) {
            throw new MaintenanceNotOwnedException(maintenanceId, vehicleId);
        }

        return MaintenanceMapper.toResponse(maintenance);
    }
}
