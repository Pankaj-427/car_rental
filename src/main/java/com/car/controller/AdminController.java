package com.car.controller;

import com.car.entity.Car;
import com.car.entity.Lease;
import com.car.service.CarService;
import com.car.service.LeaseService;
import com.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

@Autowired
private final UserService userService;
    @Autowired
    private final LeaseService leaseService;
    @Autowired
    private final CarService carService;


    public AdminController(UserService userService, LeaseService leaseService, CarService carService) {
        this.userService = userService;
        this.leaseService = leaseService;
        this.carService = carService;
    }
        /// get all user
        ///
        ///  http:/localhost:8080/api/admin/users
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

     @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        return carService.getAllCars();
    }



    @GetMapping("/leases")
    public ResponseEntity<List<Lease>> getAllLeases() {
        return leaseService.getAllLeases();
    }
    @GetMapping("/car/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.getCar(id);
    }

    @PutMapping("/car")
    public void updateCar(@RequestBody Car car) {
        carService.updateCar(car);
    }

}
