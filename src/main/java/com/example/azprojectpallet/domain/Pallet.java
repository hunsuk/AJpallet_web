package com.example.azprojectpallet.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Getter
@Setter
public class Pallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String category;
    private String name = "-";
    private String size;
    private String weight;
    private String material;
    private String uniqueness;
    private String img_uri;
    private String front;

}
