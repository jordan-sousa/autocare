package com.jordan.autocare.vehicle.controller;

import com.jordan.autocare.vehicle.dto.VehicleCreateRequest;
import com.jordan.autocare.vehicle.dto.VehicleMileageUpdateRequest;
import com.jordan.autocare.vehicle.dto.VehicleResponse;
import com.jordan.autocare.vehicle.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @Operation(summary = "Cadastrar veículos")
    @PostMapping
    public ResponseEntity<VehicleResponse> create(@RequestBody @Valid VehicleCreateRequest request) {
        VehicleResponse response = vehicleService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar veículo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.findById(id));
    }

    @Operation(summary = "Paginação")
    @GetMapping
    public ResponseEntity<Page<VehicleResponse>> findAll(
            @RequestParam(required = false)
            String brand,

            @RequestParam(required = false)
            Integer year,

            Pageable pageable
    ) {
        return ResponseEntity.ok(vehicleService.findAll(brand, year, pageable));
    }

    @Operation(summary = "Atualizar kilometragem pelo ID")
    @PatchMapping("/{id}/mileage")
    public ResponseEntity<VehicleResponse> updateMileage(@PathVariable Long id, @RequestBody @Valid VehicleMileageUpdateRequest request) {

        return ResponseEntity.ok(vehicleService.updateMileage(id, request));
    }

    @Operation(summary = "Deletar veículo por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        vehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
