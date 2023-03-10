package com.example.azprojectpallet.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class RentHistory {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Truck logTruck;

    private String logRentItem;

    private String status;

    private String index;

}
