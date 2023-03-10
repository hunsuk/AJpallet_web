package com.example.azprojectpallet.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class SendReservationInfo {
    private List<String> startDate;
    private List<String> endDate;
    private List<String> order;
    private List<Long> id;
}
