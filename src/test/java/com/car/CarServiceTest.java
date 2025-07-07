package com.car;

import com.car.entity.Car;
import com.car.repository.CarRepository;
import com.car.repository.UserRepository;
import com.car.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CarService carService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCar_WhenCarExists_ShouldReturnCar() {
        Long carId = 1L;
        Car car = new Car();
        car.setId(carId);
        car.setModel("Toyota Prius");

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car result = carService.getCar(carId);

        assertNotNull(result);
        assertEquals("Toyota Prius", result.getModel());
        verify(carRepository).findById(carId);
    }

    @Test
    void testGetCar_WhenCarDoesNotExist_ShouldThrowException() {
        Long carId = 1L;
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> carService.getCar(carId));
        verify(carRepository).findById(carId);
    }

    @Test
    void testUpdateCar_ShouldSaveCar() {
        Car car = new Car();
        car.setId(1L);
        car.setModel("Honda Civic");

        carService.updateCar(car);

        verify(carRepository).save(car);
    }
}
