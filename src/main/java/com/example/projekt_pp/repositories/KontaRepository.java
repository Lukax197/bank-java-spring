package com.example.projekt_pp.repositories;

import com.example.projekt_pp.models.Kategorie;
import com.example.projekt_pp.models.Klienci;
import com.example.projekt_pp.models.Konta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KontaRepository extends JpaRepository<Konta, Long> {

    @Modifying
    @Query("update Konta k set k.saldo=(k.saldo+:newSaldo) where k.klient.id=:id")
    void updateSaldo(long id, double newSaldo);

    @Modifying
    @Query("update Konta k set k.saldo=(k.saldo-:kwota) where k.klient.id=:id")
    void przelew(long id, double kwota);

    Konta findByKlient(Optional<Klienci> K);
}
