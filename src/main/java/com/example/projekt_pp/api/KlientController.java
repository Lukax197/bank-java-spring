package com.example.projekt_pp.api;

import com.example.projekt_pp.configuration.ProfileNames;
import com.example.projekt_pp.models.Klienci;
import com.example.projekt_pp.repositories.KlienciRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/klienci")
@Profile(ProfileNames.REST_CONTROLLER)
public class KlientController {

    @Autowired
    KlienciRepository klienciRepository;

    @GetMapping(path="/{id}")
    public Optional<Klienci> showDetails(@PathVariable long id) {
        return klienciRepository.findById(id);
    }

    @GetMapping(path="/")
    public Klienci[] showAll() {
        Klienci[] K = klienciRepository.findAll().toArray(new Klienci[0]);
        return K;
    }

    @PostMapping({"/add", "/edit"})
    public void createKlient(@RequestBody Klienci k) {
        klienciRepository.save(k);
    }

}

