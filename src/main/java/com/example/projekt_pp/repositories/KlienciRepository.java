package com.example.projekt_pp.repositories;

import com.example.projekt_pp.models.Kategorie;
import com.example.projekt_pp.models.Klienci;
import com.example.projekt_pp.models.Konta;
import com.example.projekt_pp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface KlienciRepository extends JpaRepository<Klienci, Long> {
    Klienci findClientsByName(String name);
    Klienci findClientsByBirthDate(LocalDate data_ur);
    Klienci findClientsByCategory(Kategorie kategoria);

    @Query("select k from Klienci k where k.imie = ?1")
    Klienci findClientsByNameV2(String name, String drugi);

    @Query("select k from Klienci k where k.data_urodzenia = ?1")
    Klienci findClientsByBirthDateV2(LocalDate data_ur);

    @Query("select k from Klienci k where k.kategoria = (:kategoria)")
    Klienci findClientsByCategoryV2(Kategorie kategoria);

    @Query("select k from Klienci k where (upper(k.imie)=:phase or upper(k.nazwisko)=:phase or upper(k.adres_zamieszkania)=:phase) and k.aktywny=:active")
    List<Klienci> findKlienciFilter(String phase, boolean active);

    @Query("select k from Klienci k where (upper(k.imie)=:phase or upper(k.nazwisko)=:phase or upper(k.adres_zamieszkania)=:phase) and k.aktywny=:active and k.kategoria=:kategoria")
    List<Klienci> findKlienciFilter(String phase, boolean active, Kategorie kategoria);

    @Query("select k from Klienci k where k.aktywny=:active and k.kategoria=:kategoria")
    List<Klienci> findKlienciFilter(boolean active, Kategorie kategoria);

    Klienci findByInternetBankingAccount(User u);

    @Modifying
    @Query("update Klienci k set k.imie=:imie, k.nazwisko=:nazwisko, k.adres_zamieszkania=:adres, k.data_urodzenia=:data, " +
            "k.kategoria=:kat, k.aktywny=:aktywny, k.internetBankingAccount=:u, k.konto=:konto where k.id=:id")
    void editKlientFromIndex(Long id, String imie, String nazwisko, String adres, LocalDate data, Kategorie kat, boolean aktywny, User u, Konta konto);
}