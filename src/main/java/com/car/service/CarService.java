package com.car.service;

import com.car.entity.Car;
import com.car.entity.CarStatus;
import com.car.entity.User;
import com.car.repository.CarRepository;
import com.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private  final CarRepository carRepository;
    @Autowired
    private final  UserRepository userRepository;


    public CarService(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> enrollCar(Long ownerId, Car car) {

            User owner = userRepository.findById(ownerId).orElseThrow();
            car.setOwner(owner);
            car.setStatus(CarStatus.IDLE);
        Car save = carRepository.save(car);
        return new ResponseEntity<>("saved ", HttpStatus.CREATED);
    }


    public ResponseEntity<?> getCarsByOwner(Long ownerId) {
        Optional<User> optionalOwner = userRepository.findById(ownerId);
//        User owner = userRepository.findById(ownerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
//
//        List<Car> cars = carRepository.findByOwner(owner);
//        return ResponseEntity.ok(cars);



            if (optionalOwner.isPresent()) {
                User owner = optionalOwner.get();
                List<Car> cars = carRepository.findByOwner(owner);
                return new ResponseEntity<>(cars, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


    }

    public ResponseEntity<List<Car>> getAllAvailableCars() {
        List<Car> list = carRepository.findAll().stream()
                .filter(car -> car.getStatus() == CarStatus.IDLE)
                .toList();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> all = carRepository.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }
    public Car getCar(Long id) {
        return carRepository.findById(id).orElseThrow();
    }

    public void updateCar(Car car) {
        carRepository.save(car);
    }

}
