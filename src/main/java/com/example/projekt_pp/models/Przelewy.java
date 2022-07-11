package com.example.projekt_pp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "przelewy")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Przelewy {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 1, max = 100)
    private String odbiorca_nazwa;

    @Pattern(regexp="^(0|[1-9][0-9]*)$")
    @Size(min = 10, max = 10)
    private String nr_konta_odbiorcy;

    @NumberFormat(pattern = "#.00")
    @Min(1)
    private double kwota;

    @Size(min = 1, max = 50)
    private String tytul;

    private String status;

    private long k;

    public Przelewy(String odbiorca_nazwa ,String nr_konta_odbiorcy, int kwota, String tytul) {
        this.odbiorca_nazwa = odbiorca_nazwa;
        this.nr_konta_odbiorcy = nr_konta_odbiorcy;
        this.kwota = kwota;
        this.tytul = tytul;
        this.status = "Oczekujace";
    }
}
