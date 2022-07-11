package com.example.projekt_pp.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "klienci")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name = "Klienci.findClientsByName", query = "select k from Klienci k where k.imie = ?1")
@NamedQuery(name = "Klienci.findClientsByBirthDate", query = "select k from Klienci k where k.data_urodzenia = ?1")
@NamedQuery(name = "Klienci.findClientsByCategory", query = "select k from Klienci k where k.kategoria = ?1")

public class Klienci {

    @Min(0)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min=2, max=20)
    @Column(name = "imie")
    private String imie;

    @NotBlank
    @Size(min=2, max=35)
    @Column(name = "nazwisko")
    private String nazwisko;

    @Size(min=2, max=50)
    @Column(name = "adres_zamieszkania")
    private String adres_zamieszkania;

    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_urodzenia")
    private LocalDate data_urodzenia;

    @Column(name = "aktywny")
    private boolean aktywny;

    @OneToOne(mappedBy = "klient", cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Konta konto;

    @Valid
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "internet_banking_account_id")
    private User internetBankingAccount;

    @ManyToOne
    @JsonManagedReference
    Kategorie kategoria;

    public Klienci(long id, String imie, String nazwisko, String adres_zamieszkania, LocalDate data_urodzenia, Kategorie kategoria){
        this.id = id;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adres_zamieszkania = adres_zamieszkania;
        this.data_urodzenia = data_urodzenia;
        this.kategoria = kategoria;
        aktywny = true;
    }
}
