package com.example.azprojectpallet.dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDataPallet {
    private ArrayList<String> id;
    private ArrayList<String> manage;
}
