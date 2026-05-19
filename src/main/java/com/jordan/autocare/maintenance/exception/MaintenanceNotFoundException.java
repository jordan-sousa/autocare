package com.jordan.autocare.maintenance.exception;

public class MaintenanceNotFoundException extends RuntimeException {
    public MaintenanceNotFoundException(Long id) {
        super("Manutenção com ID " + id + " não encontrada");
    }
}
