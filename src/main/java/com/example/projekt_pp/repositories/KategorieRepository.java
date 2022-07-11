package com.example.projekt_pp.repositories;

import com.example.projekt_pp.models.Kategorie;
import com.example.projekt_pp.models.Klienci;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategorieRepository extends JpaRepository<Kategorie, Long> {
    Kategorie findKategorieByNazwa(String name);
}
