package com.example.azprojectpallet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RentItem {
    @Id
    @GeneratedValue
    private Long id;
    private String managePart;
    private String position;
    private PalleItemState status;
    private String palletId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentStartDay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentReturnDay;
    @ManyToOne
    private User rentAccount;
    @ManyToOne
    private Spot ownerSpot;

    @ManyToOne
    private Truck ownerTruck;

}
