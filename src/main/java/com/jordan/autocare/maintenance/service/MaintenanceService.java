package com.jordan.autocare.maintenance.service;

import com.jordan.autocare.maintenance.domain.Maintenance;
import com.jordan.autocare.maintenance.dto.MaintenanceCreateRequest;
import com.jordan.autocare.maintenance.dto.MaintenanceResponse;
import com.jordan.autocare.maintenance.mapper.MaintenanceMapper;
import com.jordan.autocare.maintenance.repository.MaintenanceRepository;
import com.jordan.autocare.vehicle.domain.Vehicle;
import com.jordan.autocare.vehicle.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
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

        if (request.mileagePerformed() < vehicle.getCurrentMileage()) {
            throw new IllegalArgumentException(
                    "Quilometragem da manuteção não pode ser menor que a  quilometragem atual do veículo"
            );
        }

        Maintenance maintenance = Maintenance.builder()
                .type(request.type())
                .date(request.date())
                .description(request.description())
                .mileagePerformed(request.nextMaintenanceMileage())
                .vehicle(vehicle)
                .build();

        Maintenance saved = maintenanceRepository.save(maintenance);

        return MaintenanceMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public MaintenanceResponse findById(Long vehicleId, Long maintenanceId) {
        Maintenance maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new EntityNotFoundException("Manutenção não encontrada!"));

        if (!maintenance.getVehicle().getId().equals(vehicleId)) {
            throw new IllegalArgumentException("Manutençao não pertence ao veículo informado!");
        }

        return MaintenanceMapper.toResponse(maintenance);
    }
}
