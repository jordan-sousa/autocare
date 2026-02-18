package com.jordan.autocare.vehicle.service;

import com.jordan.autocare.vehicle.domain.Vehicle;
import com.jordan.autocare.vehicle.dto.VehicleCreateRequest;
import com.jordan.autocare.vehicle.dto.VehicleMileageUpdateRequest;
import com.jordan.autocare.vehicle.dto.VehicleResponse;
import com.jordan.autocare.vehicle.mapper.VehicleMapper;
import com.jordan.autocare.vehicle.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Transactional
    public VehicleResponse create(VehicleCreateRequest request) {

        Vehicle vehicle = Vehicle.builder()
                .brand(request.brand())
                .model(request.model())
                .year(request.year())
                .currentMileage(request.currentMileage())
                .build();

        Vehicle saved = vehicleRepository.save(vehicle);

        return VehicleMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public VehicleResponse findById(Long id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veiculo não encontrado"));

        return VehicleMapper.toResponse(vehicle);
    }

    @Transactional(readOnly = true)
    public Vehicle findEntityById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado"));
    }


    @Transactional
    public VehicleResponse updateMileage(Long id, VehicleMileageUpdateRequest request) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Veiculo não encontrado"));

        if (request.newMileage() < vehicle.getCurrentMileage()) {
            throw  new IllegalArgumentException("Nova quilometragem nãõ pode ser menor que a atual");
        }

        vehicle.setCurrentMileage(request.newMileage());

        return VehicleMapper.toResponse(vehicle);
    }

    @Transactional(readOnly = true)
    public List<VehicleResponse> findAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(VehicleMapper::toResponse)
                .toList();
    }
}
