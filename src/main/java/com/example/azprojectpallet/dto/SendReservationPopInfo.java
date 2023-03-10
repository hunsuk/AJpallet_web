package com.example.azprojectpallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SendReservationPopInfo {
    private String startDay;
    private String EndDay;
    private String type;
    private int count;
    private int cost;
}
