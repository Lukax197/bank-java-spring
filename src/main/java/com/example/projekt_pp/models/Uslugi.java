package com.example.projekt_pp.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name="uslugi")
@Getter
@Setter
@NoArgsConstructor
public class Uslugi {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    public Uslugi(String name){
        this.name = name;
    }
}
