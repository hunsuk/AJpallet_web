    package com.example.azprojectpallet.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Spot {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int inCount;
    private int outCount;
    private int lossCount;

    @OneToMany(mappedBy = "manageSpot")
    private List<Reservation> manageReservations;

    @OneToMany(mappedBy = "ownerSpot")
    private List<RentItem> inventoryPallets;
}
