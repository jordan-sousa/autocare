package com.jordan.autocare.maintenance.repository;

import com.jordan.autocare.maintenance.domain.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByVehicleId(Long vehicleId);

    Optional<Maintenance> findByIdAndVehicleId(Long maintenanceId, Long vehicleId);
}
