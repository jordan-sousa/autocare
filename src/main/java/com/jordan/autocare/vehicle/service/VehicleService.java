package com.jordan.autocare.vehicle.service;

import com.jordan.autocare.vehicle.domain.Vehicle;
import com.jordan.autocare.vehicle.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Transactional(readOnly = true)
    public Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }

    @Transactional
    public Vehicle updateMileage(Long vehicleId, Integer newMileage) {
        Vehicle vehicle = findById(vehicleId);

        vehicle.updateMileage(newMileage);

        return vehicleRepository.save(vehicle);
    }
}
