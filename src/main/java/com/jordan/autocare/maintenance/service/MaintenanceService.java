package com.jordan.autocare.maintenance.service;

import com.jordan.autocare.maintenance.domain.Maintenance;
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
    public Maintenance registerMaintenance(Long vehicleId, Maintenance maintenance) {

        Vehicle vehicle = vehicleService.findById(vehicleId);

        if (maintenance.getMileagePerformed() < vehicle.getCurrentMileage()) {
            throw new IllegalArgumentException(
                    "Quilometragem da manuteção não pode ser menor que a  quilometragem atual do veículo"
            );
        }

        maintenance.setVehicle(vehicle);

        maintenance.calculateStatus(vehicle.getCurrentMileage());

        return maintenanceRepository.save(maintenance);
    }

    @Transactional(readOnly = true)
    public Maintenance findById(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manutençao não encontrada"));
    }
}
