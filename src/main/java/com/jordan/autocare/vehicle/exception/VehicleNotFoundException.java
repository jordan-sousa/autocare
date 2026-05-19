package com.jordan.autocare.vehicle.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(Long id) {
        super("Veículo com ID " + id + " não encontrado");
    }
}
