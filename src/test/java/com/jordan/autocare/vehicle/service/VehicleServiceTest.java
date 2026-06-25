package com.jordan.autocare.vehicle.service;

import com.jordan.autocare.auth.repository.UserRepository;
import com.jordan.autocare.vehicle.domain.Vehicle;
import com.jordan.autocare.vehicle.dto.VehicleResponse;
import com.jordan.autocare.vehicle.exception.VehicleNotFoundException;
import com.jordan.autocare.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VehicleService vehicleService;

    @Test
    void shoudFindVehicleById() {
        Vehicle vehicle = Vehicle.builder()
                .id(1L)
                .brand("Fiat")
                .model("Mobi")
                .year(2018)
                .currentMileage(135000)
                .build();

        when(vehicleRepository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        VehicleResponse response = vehicleService.findById(1L);

        assertEquals("Fiat", response.brand());
    }

    @Test
    void shouldThrowVehicleNotFoundExceptionWhenVehicleDoesNotExist() {
        when(vehicleRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                VehicleNotFoundException.class,
                () -> vehicleService.findById(99L)
        );
    }
}
