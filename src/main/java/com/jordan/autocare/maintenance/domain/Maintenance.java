package com.jordan.autocare.maintenance.domain;

import com.jordan.autocare.vehicle.domain.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
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

    private String description;

    private Integer mileagePerformed;
    private Integer nextMaintenanceMileage;

//    @Enumerated(EnumType.STRING)
//    private MaintenanceStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    public MaintenanceStatus calculateStatus() {
        if (vehicle == null || vehicle.getCurrentMileage() == null || nextMaintenanceMileage == null) {
            return MaintenanceStatus.EM_DIA;
        }

        Integer currentMileage = vehicle.getCurrentMileage();

        if (currentMileage >= nextMaintenanceMileage) {
            return MaintenanceStatus.ATRASADA;
        }

        int difference = nextMaintenanceMileage - currentMileage;

        if (difference <= 1000) {
            return MaintenanceStatus.ATENCAO;
        }

        return MaintenanceStatus.EM_DIA;
    }
}
