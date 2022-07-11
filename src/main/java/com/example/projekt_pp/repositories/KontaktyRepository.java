package com.example.projekt_pp.repositories;

import com.example.projekt_pp.models.Konta;
import com.example.projekt_pp.models.Kontakty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KontaktyRepository extends JpaRepository<Kontakty, Long> {

    List<Kontakty> findAllByK(long id);
}
