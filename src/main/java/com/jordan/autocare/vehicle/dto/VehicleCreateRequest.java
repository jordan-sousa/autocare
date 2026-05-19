package com.jordan.autocare.vehicle.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VehicleCreateRequest(

        @NotBlank(message = "A marca é obrigatória")
        String brand,

        @NotBlank(message = "O modelo é obrigatório")
        String model,

        @NotNull(message = "O ano é obrigatório")
        Integer year,

        @NotNull(message = "É necessário informar a quilometragem atual")
        @Positive(message = "A quilometragem deve ser positiva")
        Integer currentMileage,

        @NotNull(message = "O ID do usuário é obrigatório")
        Long userId
) {
}
