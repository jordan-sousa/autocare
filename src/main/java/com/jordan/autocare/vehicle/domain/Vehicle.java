package com.jordan.autocare.vehicle.domain;

import com.jordan.autocare.auth.domain.User;
import com.jordan.autocare.maintenance.domain.Maintenance;
import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(of = "id")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private Integer year;

    @PositiveOrZero
    private Integer currentMileage;

    @OneToMany(
            mappedBy = "vehicle",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<Maintenance> maintenances = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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
    public void preUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    public void updateMileage(Integer newMileage) {
        validateMileage(newMileage);

        this.currentMileage = newMileage;
    }

    public void validateMaintenanceMileage(Integer mileagePerformed) {
        if (mileagePerformed < this.currentMileage) {
            throw new IllegalArgumentException(
                    "Quilometragem da manutenção não pode ser menor que a quilometragem atual do veículo"
            );
        }
    }

    private void validateMileage(Integer mileage) {
        if (mileage == null) {
            throw new IllegalArgumentException("A quilometragem não pode ser nula.");
        }

        if (mileage < this.currentMileage) {
            throw new IllegalArgumentException("A quilometragem não pode diminuir");
        }

        if (mileage > 2_000_000) {
            throw new IllegalArgumentException("Quilometragem inválida");
        }
    }
}
