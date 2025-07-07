package com.car.service;

import com.car.entity.User;
import com.car.repository.CarRepository;
import com.car.repository.LeaseRepository;
import com.car.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<?> registerUser(User user) {
        User save = userRepository.save(user);
        return new ResponseEntity<>("Add successfully ", HttpStatus.CREATED);


    }

    public ResponseEntity<?> getAllUsers() {

        List<User> all = userRepository.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);


    }




}
