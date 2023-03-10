package com.example.azprojectpallet.repo;

import com.example.azprojectpallet.domain.Reservation;
import com.example.azprojectpallet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findByOrderAccount(User user);
}
