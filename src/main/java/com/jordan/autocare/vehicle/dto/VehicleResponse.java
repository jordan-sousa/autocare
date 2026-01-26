package com.jordan.autocare.vehicle.dto;

public record VehicleResponse(Long id,
                              String brand,
                              String model,
                              Integer year,
                              Integer currentMileage) {
}
