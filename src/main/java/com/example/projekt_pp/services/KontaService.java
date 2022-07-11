package com.example.projekt_pp.services;

import com.example.projekt_pp.models.Konta;
import com.example.projekt_pp.repositories.KontaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class KontaService {

    private final KontaRepository kontaRepository;

    public void dodajKonto(Konta k) {kontaRepository.save(k);}

    public void edytujSaldo(long id, double kwota) {
        kontaRepository.updateSaldo(id, kwota);
    }

    public void przelew(long id, double kwota) {
        kontaRepository.przelew(id, kwota);
    }
}
