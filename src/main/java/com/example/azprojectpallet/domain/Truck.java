package com.example.azprojectpallet.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
public class Truck {
    @Id
    @GeneratedValue
    private Long id;
    private String carNumber;
    @OneToMany(mappedBy = "ownerTruck")
    private List<RentItem> truckInPallets;
    @OneToMany(mappedBy = "orderTruck")
    private List<Reservation> truckReservation;

    @OneToMany(mappedBy = "logTruck")
    private List<RentHistory>rentHistories;
}
