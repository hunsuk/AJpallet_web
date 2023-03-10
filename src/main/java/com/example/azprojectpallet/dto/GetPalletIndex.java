package com.example.azprojectpallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPalletIndex {
    private String lat;
    private String lon;
    private String id;
}
