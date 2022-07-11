package com.example.projekt_pp.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "konta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Konta {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "nr_konta")
    private long nr_konta;

    @Column(name = "pin")
    private long pin;

    @NumberFormat(pattern = "#.00")
    @Column(name = "saldo")
    private double saldo;

    @OneToOne
    @JsonBackReference
    Klienci klient;

    @JsonManagedReference
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Uslugi> uslugi=new HashSet<>();

    public Konta(long nr_konta, long pin, Klienci klient){
        this.nr_konta = nr_konta;
        this.pin = pin;
        this.klient = klient;
        this.saldo = 0;
    }

    public Konta(long nr_konta, long pin, Klienci klient, Set<Uslugi> uslugi){
        this.nr_konta = nr_konta;
        this.pin = pin;
        this.klient = klient;
        this.uslugi = uslugi;
        this.saldo = 0;
    }
}
