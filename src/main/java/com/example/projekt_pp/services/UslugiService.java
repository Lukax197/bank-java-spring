package com.example.projekt_pp.services;

import com.example.projekt_pp.models.Uslugi;
import com.example.projekt_pp.repositories.UslugiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class UslugiService {

    private final UslugiRepository uslugiRepository;

    public List<Uslugi> getAll() {return uslugiRepository.findAll();}
}
