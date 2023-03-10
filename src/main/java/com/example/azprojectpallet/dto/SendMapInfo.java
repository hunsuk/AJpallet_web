package com.example.azprojectpallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SendMapInfo {
    private List<String> latitude;
    private List<String> hardness;

    private List<Integer> status;
    private List<String> id;
}
