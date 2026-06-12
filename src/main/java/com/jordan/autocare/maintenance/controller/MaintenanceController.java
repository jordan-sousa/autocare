package com.jordan.autocare.maintenance.controller;

import com.jordan.autocare.maintenance.dto.MaintenanceCreateRequest;
import com.jordan.autocare.maintenance.dto.MaintenanceResponse;
import com.jordan.autocare.maintenance.service.MaintenanceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/vehicles/{vehicleId}/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @Operation(summary = "Cadastrar manutenções")
    @PostMapping
    public ResponseEntity<MaintenanceResponse> register(@PathVariable Long vehicleId, @RequestBody @Valid MaintenanceCreateRequest request) {
        MaintenanceResponse response = maintenanceService.registerMaintenance(vehicleId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Buscar manutenção pelo ID")
    @GetMapping("/{maintenanceId}")
    public ResponseEntity<MaintenanceResponse> findById(@PathVariable Long vehicleId, @PathVariable Long maintenanceId) {

        return ResponseEntity.ok(
                maintenanceService.findById(vehicleId, maintenanceId)
        );
    }

    @Operation(summary = "Deletar manutenção pelo ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<MaintenanceResponse> delete(@PathVariable Long id) {

        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}
