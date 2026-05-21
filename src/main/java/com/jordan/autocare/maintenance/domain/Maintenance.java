package com.jordan.autocare.maintenance.domain;

import com.jordan.autocare.auth.domain.User;
import com.jordan.autocare.vehicle.domain.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenances")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = "id")
public class Maintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaintenanceType type;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 600)
    private String description;

    @Positive
    @Column(nullable = false)
    private Integer mileagePerformed;

    @Positive
    @Column(nullable = false)
    private Integer nextMaintenanceMileage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updateAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updateAt = LocalDateTime.now();
    }

    public MaintenanceStatus calculateStatus() {
        if (vehicle == null || nextMaintenanceMileage == null) {
            return MaintenanceStatus.EM_DIA;
        }

        int currentMileage = vehicle.getCurrentMileage();

        if (currentMileage >= nextMaintenanceMileage) {
            return MaintenanceStatus.ATRASADA;
        }

        int difference = nextMaintenanceMileage - currentMileage;

        if (difference <= 1000) {
            return MaintenanceStatus.ATENCAO;
        }

        return MaintenanceStatus.EM_DIA;
    }

    public void validateMaintenanceMileage() {
        if (mileagePerformed < vehicle.getCurrentMileage()) {
            throw new IllegalArgumentException("A quilometragem de manutenção não pode ser inferior à quilometragem atual do veículo.");
        }

        if (nextMaintenanceMileage <= mileagePerformed) {
            throw new IllegalArgumentException("A próxima quilometragem para manutenção deve ser maior que a quilometragem já realizada.");
        }
    }
}
