package com.example.projekt_pp.services;

import com.example.projekt_pp.models.Kontakty;
import com.example.projekt_pp.repositories.KontaktyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class KontaktyService {

    private KontaktyRepository kontaktyRepository;

    public List<Kontakty> getKontakty(long id){
        return kontaktyRepository.findAllByK(id);
    }

    public void addKontakt(Kontakty kontakt) {
        kontaktyRepository.save(kontakt);
    }
}
