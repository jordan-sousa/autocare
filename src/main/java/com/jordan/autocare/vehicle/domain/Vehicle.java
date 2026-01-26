package com.jordan.autocare.vehicle.domain;

import com.jordan.autocare.auth.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private Integer year;

    private Integer currentMileage;

    @ManyToOne
    private User user;

    public void updateMileage(Integer newMeleage) {
        if (newMeleage < this.currentMileage) {
            throw new IllegalArgumentException("Quilometragem não pode diminuir");
        }
        this.currentMileage = newMeleage;
    }
}
