package com.jordan.autocare.maintenance.domain;

import com.jordan.autocare.vehicle.domain.Vehicle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MaintenanceType type;

    private LocalDate date;

    private Integer mileagePerformed;
    private Integer nextMaintenanceMileage;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    @ManyToOne
    private Vehicle vehicle;

    public void calculateStatus(Integer currentVehicleMileage) {
        if (currentVehicleMileage >= nextMaintenanceMileage) {
            this.status = MaintenanceStatus.ATRASADA;
        } else if (nextMaintenanceMileage - currentVehicleMileage <= 1000) {
            this.status = MaintenanceStatus.ATENCAO;
        } else {
            this.status = MaintenanceStatus.EM_DIA;
        }
    }
}
