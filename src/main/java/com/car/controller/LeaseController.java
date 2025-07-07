package com.car.controller;

import com.car.entity.Lease;
import com.car.service.LeaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lease")
public class LeaseController {

    private final LeaseService leaseService;

    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    // save th edata


    @PostMapping("/start/{customerId}/{carId}")
    public ResponseEntity<?> startLease(@PathVariable Long customerId, @PathVariable Long carId) {

        return leaseService.startLease(customerId, carId);
    }

    @PostMapping("/end/{customerId}/{carId}")
    public ResponseEntity<?> endLease(@PathVariable Long customerId, @PathVariable Long carId) {
        return leaseService.endLease(customerId, carId);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Lease>> getLeaseHistoryForCustomer(@PathVariable Long customerId) {
        return leaseService.getLeaseHistory(customerId);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<String>> getLeaseHistoryForOwner(@PathVariable Long ownerId) {
        return leaseService.getLeaseHistoryForOwner(ownerId);
    }

    @GetMapping("/all/lease")
    public ResponseEntity<List<Lease>> getAllLeases() {
        return leaseService.getAllLeases();
    }

}
