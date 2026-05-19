package com.jordan.autocare.vehicle.service;

import com.jordan.autocare.vehicle.domain.Vehicle;
import com.jordan.autocare.vehicle.dto.VehicleCreateRequest;
import com.jordan.autocare.vehicle.dto.VehicleMileageUpdateRequest;
import com.jordan.autocare.vehicle.dto.VehicleResponse;
import com.jordan.autocare.vehicle.exception.VehicleNotFoundException;
import com.jordan.autocare.vehicle.mapper.VehicleMapper;
import com.jordan.autocare.vehicle.repository.VehicleRepository;
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
                .orElseThrow(() -> new VehicleNotFoundException(id));

        return VehicleMapper.toResponse(vehicle);
    }

    @Transactional(readOnly = true)
    public Vehicle findEntityById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));
    }

    @Transactional
    public VehicleResponse updateMileage(Long id, VehicleMileageUpdateRequest request) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException(id));

        vehicle.updateMileage(request.newMileage());

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
