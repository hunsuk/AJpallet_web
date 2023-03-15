package com.example.azprojectpallet.domain;

import com.example.azprojectpallet.repo.UserRepository;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
@Setter
@Getter
@NoArgsConstructor

public class Reservation extends BaseTime{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentEndDate;
    private String orderPallet;
    private String orderModify;
    private String status;
    @ManyToOne
    private User orderAccount;
    @ManyToOne
    private Truck orderTruck;
    @ManyToOne
    private Spot manageSpot;




}