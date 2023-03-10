package com.example.azprojectpallet.repo;

import com.example.azprojectpallet.domain.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TruckRepository extends JpaRepository<Truck,Long> {
}
