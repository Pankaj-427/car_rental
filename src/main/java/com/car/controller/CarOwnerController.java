package com.car.controller;

import com.car.entity.Car;
import com.car.service.CarService;
import com.car.service.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/car")
public class CarOwnerController {

    @Autowired
    private  final CarService carService;

    @Autowired
    private final LeaseService leaseService;


    public CarOwnerController(CarService carService, LeaseService leaseService) {
        this.carService = carService;
        this.leaseService = leaseService;
    }

    ///  save the car
// http://localhost:8080/api/car/1/cars
    @PostMapping("/{ownerId}/cars")
    public ResponseEntity<?> enrollCar(@PathVariable Long ownerId, @RequestBody Car car) {
        return carService.enrollCar(ownerId, car);
    }

    // get the car by owner id

// http://localhost:8080/api/car/1/cars
    @GetMapping("/{ownerId}/cars")
    public ResponseEntity<?> getCarsByOwner(@PathVariable Long ownerId) {

        return carService.getCarsByOwner(ownerId);
    }

  @GetMapping("/{ownerId}/cars/history")
    public ResponseEntity<List<String>> getLeaseHistory(@PathVariable Long ownerId) {
        return leaseService.getLeaseHistoryForOwner(ownerId);
    }
}
