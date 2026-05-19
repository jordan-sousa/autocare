package com.jordan.autocare.maintenance.exception;

public class MaintenanceNotOwnedException extends RuntimeException {
    public MaintenanceNotOwnedException(Long maintenanceId, Long vehicleId) {
        super("Manutenção " + maintenanceId + " não pertence ao veículo " + vehicleId);
    }
}
