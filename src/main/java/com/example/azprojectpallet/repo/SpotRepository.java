package com.example.azprojectpallet.repo;

import com.example.azprojectpallet.domain.Spot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot,Long> {
}
