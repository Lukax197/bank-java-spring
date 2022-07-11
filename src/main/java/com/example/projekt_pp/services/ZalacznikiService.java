package com.example.projekt_pp.services;

import com.example.projekt_pp.models.Zalaczniki;
import com.example.projekt_pp.repositories.ZalacznikiRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ZalacznikiService {

    private final ZalacznikiRepository zalacznikiRepository;

    public void addZalacznik(String nazwa, MultipartFile multipartFile) throws IOException {
        Zalaczniki z = new Zalaczniki();

        if(multipartFile.isEmpty() == false) {//to powinno być w serwisie
            z.setNazwa(nazwa);
            z.setFileName(multipartFile.getOriginalFilename());
            z.setFileContent(multipartFile.getBytes());
            zalacznikiRepository.save(z);
        }
    }

    public List<Zalaczniki> getAll(){
        return zalacznikiRepository.findAll();
    }

    public Zalaczniki getById(long id) {
        return zalacznikiRepository.getById(id);
    }
}
