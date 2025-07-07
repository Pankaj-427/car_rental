package com.car.controller;

import com.car.entity.Car;
import com.car.entity.Lease;
import com.car.entity.User;
import com.car.service.CarService;
import com.car.service.LeaseService;
import com.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cust")
public class CustomerController {

    @Autowired
    private UserService userService;
    @Autowired
    private CarService carService;
    @Autowired
    private LeaseService leaseService;
 ///     api/cust/register
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody User user) {
        return userService.registerUser(user);

    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAvailableCars() {
        return carService.getAllAvailableCars();
    }

    @PostMapping("/{customerId}/lease/{carId}")
    public ResponseEntity<?> startLease(@PathVariable Long customerId, @PathVariable Long carId) {
        return leaseService.startLease(customerId, carId);
    }

    @PostMapping("/{customerId}/end/{carId}")
    public ResponseEntity<?> endLease(@PathVariable Long customerId, @PathVariable Long carId) {
        return leaseService.endLease(customerId, carId);
    }

    @GetMapping("/{customerId}/history")
    public ResponseEntity<List<Lease>>  getLeaseHistory(@PathVariable Long customerId) {
        return leaseService.getLeaseHistory(customerId);
    }


}
