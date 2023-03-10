package com.example.azprojectpallet.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SendAdminReservationInfo {
    private Long id;
    private String customer;
    private String orderDay;
    private String getDay;
    private String returnDay;
    private String spot;
    private String truckNumber;
    private String status;
    private String orderIntent;

}
