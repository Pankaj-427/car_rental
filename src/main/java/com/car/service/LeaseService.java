package com.car.service;

import com.car.entity.Car;
import com.car.entity.CarStatus;
import com.car.entity.Lease;
import com.car.entity.User;
import com.car.repository.CarRepository;
import com.car.repository.LeaseRepository;
import com.car.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class LeaseService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final LeaseRepository leaseRepository;

    public LeaseService(UserRepository userRepository, CarRepository carRepository, LeaseRepository leaseRepository) {
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.leaseRepository = leaseRepository;
    }
//    public List<String> getLeaseHistoryForOwner(Long ownerId) {
//
//        User customer = userRepository.findById(customerId).orElseThrow();
//        return leaseRepository.findByCustomer(customer);
//    }

    public ResponseEntity<?> startLease(Long customerId, Long carId) {

        User customer = userRepository.findById(customerId).orElseThrow();
        Car car = carRepository.findById(carId).orElseThrow(() -> new RuntimeException("Customer not found"));;

        List<Lease> currentLeases = leaseRepository.findByCustomer(customer)
                .stream().filter(l -> l.getEndDate() == null).toList();

        if (currentLeases.size() >= 2)
            throw new RuntimeException("Customer already has 2 active leases");

        if (car.getStatus() != CarStatus.IDLE) {
            throw new RuntimeException("Car is not available for lease");
        }

        car.setStatus(CarStatus.ON_LEASE);
        carRepository.save(car);

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        lease.setStartDate(LocalDateTime.now());

        Lease save = leaseRepository.save(lease);

        return new ResponseEntity<>(save,HttpStatus.CREATED);

    }

    public ResponseEntity<?> endLease(Long customerId, Long carId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        Lease lease = leaseRepository.findByCustomer(customer).stream()
                .filter(l -> l.getCar().equals(car) && l.getEndDate() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Active lease not found"));

        lease.setEndDate(LocalDateTime.now());
        leaseRepository.save(lease);

        car.setStatus(CarStatus.IDLE);
        carRepository.save(car);

        return new ResponseEntity<>(lease,HttpStatus.OK);
    }
    public ResponseEntity<List<Lease>> getLeaseHistory(Long customerId) {


        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<Lease> byCustomer = leaseRepository.findByCustomer(customer);

        return new ResponseEntity<>(byCustomer,HttpStatus.OK);


    }


    public  ResponseEntity<List<String>> getLeaseHistoryForOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        List<String> list = carRepository.findByOwner(owner).stream()
                .flatMap(car -> leaseRepository.findByCar(car).stream())
                .map(l -> "Car: " + l.getCar().getModel()
                        + " leased by " + l.getCustomer().getName()
                        + (l.getEndDate() != null ? " (ended)" : " (ongoing)"))
                .toList();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }


    public ResponseEntity<List<Lease>> getAllLeases() {
        List<Lease> all = leaseRepository.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }
}
