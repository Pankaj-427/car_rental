package com.car.repository;

import com.car.entity.Car;
import com.car.entity.Lease;
import com.car.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCustomer(User customer);

    List<Lease> findByCar(Car car);
}