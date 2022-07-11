package com.example.projekt_pp.controllers;

import com.example.projekt_pp.models.Zalaczniki;
import com.example.projekt_pp.repositories.ZalacznikiRepository;
import com.example.projekt_pp.services.ZalacznikiService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class DoPobraniaController {

    private ZalacznikiService zalacznikiService;

    @GetMapping("/doPobrania")
    public String getPage(Model m) {
        m.addAttribute("zalaczniki", zalacznikiService.getAll());
        return "zalaczniki";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/dodajZalacznik")
    public String addZalacznik(String nazwa, MultipartFile multipartFile) throws IOException {
        zalacznikiService.addZalacznik(nazwa,multipartFile);
        return "redirect:/doPobrania";
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam(value="id") Long id) {
        Zalaczniki dbFile = zalacznikiService.getById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getFileContent()));
    }
}
