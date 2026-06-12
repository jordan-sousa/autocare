package com.jordan.autocare.vehicle.service;

import com.jordan.autocare.auth.domain.User;
import com.jordan.autocare.auth.repository.UserRepository;
import com.jordan.autocare.vehicle.domain.Vehicle;
import com.jordan.autocare.vehicle.dto.VehicleCreateRequest;
import com.jordan.autocare.vehicle.dto.VehicleMileageUpdateRequest;
import com.jordan.autocare.vehicle.dto.VehicleResponse;
import com.jordan.autocare.vehicle.exception.VehicleNotFoundException;
import com.jordan.autocare.vehicle.mapper.VehicleMapper;
import com.jordan.autocare.vehicle.repository.VehicleRepository;
import com.jordan.autocare.vehicle.specification.VehicleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Transactional
    public VehicleResponse create(VehicleCreateRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = (User) authentication.getPrincipal();

        Vehicle vehicle = Vehicle.builder()
                .brand(request.brand())
                .model(request.model())
                .year(request.year())
                .currentMileage(request.currentMileage())
                .user(user)
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
    public Page<VehicleResponse> findAll(String brand, Integer year, Pageable pageable) {

        Specification<Vehicle> spec = Specification.where(VehicleSpecification.brandEquals(brand)).
                and(VehicleSpecification.yearEquals(year));

        return vehicleRepository.findAll(spec, pageable)
                .map(VehicleMapper::toResponse);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
}
