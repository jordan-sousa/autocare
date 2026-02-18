package com.jordan.autocare.vehicle.mapper;

import com.jordan.autocare.vehicle.domain.Vehicle;
import com.jordan.autocare.vehicle.dto.VehicleResponse;

public class VehicleMapper {
    public static VehicleResponse toResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getCurrentMileage()
        );
    }
}
