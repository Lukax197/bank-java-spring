package com.example.projekt_pp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "kategorie")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kategorie {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "typ")
    private String nazwa;

    public Kategorie(String nazwa){
        this.nazwa = nazwa;
    }
}
