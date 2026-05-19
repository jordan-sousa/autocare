package com.jordan.autocare.vehicle.controller;

import com.jordan.autocare.vehicle.dto.VehicleCreateRequest;
import com.jordan.autocare.vehicle.dto.VehicleMileageUpdateRequest;
import com.jordan.autocare.vehicle.dto.VehicleResponse;
import com.jordan.autocare.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<VehicleResponse> create(@RequestBody @Valid VehicleCreateRequest request) {
        VehicleResponse response = vehicleService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponse>> findAll() {
        return ResponseEntity.ok(vehicleService.findAll());
    }

    @PatchMapping("/{id}/mileage")
    public ResponseEntity<VehicleResponse> updateMileage(@PathVariable Long id, @RequestBody @Valid VehicleMileageUpdateRequest request) {

        return ResponseEntity.ok(vehicleService.updateMileage(id, request));
    }
}
