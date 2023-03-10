package com.example.azprojectpallet.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class GetReservationInfo {
    private String checked;
    private String count;
    private String startDay;
    private String endDay;
}
