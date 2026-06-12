package com.jordan.autocare.vehicle.specification;

import com.jordan.autocare.vehicle.domain.Vehicle;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {

    public static Specification<Vehicle> brandEquals(String brand) {
        return (root, query, criteriaBuilder) ->
                brand == null
                        ? null
                        : criteriaBuilder.equal(
                                criteriaBuilder.lower(root.get("brand")),
                                brand.toLowerCase()
        );
    }

    public static Specification<Vehicle> yearEquals(Integer year) {

        return (root, query, criteriaBuilder) ->

                year == null
                        ? null
                        : criteriaBuilder.equal(
                                root.get("year"),
                                year
                );
    }
}
