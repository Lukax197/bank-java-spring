package com.example.projekt_pp.services;

import com.example.projekt_pp.models.Kategorie;
import com.example.projekt_pp.repositories.KategorieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KategorieService {

    private final KategorieRepository kategorieRepository;

    public Kategorie getKategoria(String name) {
        return kategorieRepository.findKategorieByNazwa(name);
    }

    public List<Kategorie> getAll() {return kategorieRepository.findAll();}
}
