package com.example.projekt_pp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "kontakty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kontakty {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 1, max = 100)
    private String nazwa;

    @Pattern(regexp="^(0|[1-9][0-9]*)$")
    @Size(min = 10, max = 10)
    private String nr_konta;

    private long k;

    public Kontakty(String nazwa, String nr_konta) {
        this.nazwa = nazwa;
        this.nr_konta = nr_konta;
    }
}
