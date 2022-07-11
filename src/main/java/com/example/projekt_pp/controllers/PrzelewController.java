package com.example.projekt_pp.controllers;

import com.example.projekt_pp.models.Klienci;
import com.example.projekt_pp.models.Przelewy;
import com.example.projekt_pp.models.User;
import com.example.projekt_pp.services.*;
import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.FileNotFoundException;

@Controller
@AllArgsConstructor
public class PrzelewController {

    private PrzelewyService przelewyService;
    private UserServiceImpl userService;
    private KlienciService klienciService;
    private KontaService kontaService;
    private KontaktyService kontaktyService;

    @Secured("ROLE_USER")
    @GetMapping("/przelew")
    public String getPrzelewPage(Model m, Authentication auth){
        User u = userService.getUser(auth.getName());
        Klienci k = klienciService.getKlientByInternetBankingAccount(u);
        m.addAttribute("przelew", new Przelewy());
        m.addAttribute("konto", k.getKonto());
        m.addAttribute("kontakty", kontaktyService.getKontakty(k.getId()));
        return "przelew";
    }

    @Secured("ROLE_USER")
    @PostMapping("/przelew")
    public String getPrzelewPage(@ModelAttribute("przelew") @Valid Przelewy p, BindingResult result, Authentication auth) {

        if(result.hasErrors()){
            return "przelew";
        }

        User u = userService.getUser(auth.getName());
        Klienci k = klienciService.getKlientByInternetBankingAccount(u);

        if(k.getKonto().getSaldo() < p.getKwota()) {
            return "redirect:/przelew?err=true";
        }

        p.setK(k.getId());
        przelewyService.addPrzelew(p);
        kontaService.przelew(k.getId(), p.getKwota());
        return "redirect:/historiaOper";
    }

    @Secured("ROLE_USER")
    @GetMapping("/historiaOper")
    public String getHistoriaPage(Model m, Authentication auth) {
        User u = userService.getUser(auth.getName());
        Klienci k = klienciService.getKlientByInternetBankingAccount(u);
        m.addAttribute("przelewList", przelewyService.getHistoria(k.getId()));
        return "historiaOper";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/przelewyList")
    public String getPrzelewyList(Model m) {
        m.addAttribute("oczekujace", przelewyService.getOczekujace());
        m.addAttribute("potwierdzone", przelewyService.getPotwierdzone());
        return "przelewyList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/potwierdzPrzelew")
    public String potwierdz(@RequestParam("id") long id) {
        przelewyService.changeStatus(id);
        return "redirect:/przelewyList";
    }

    @Secured("ROLE_USER")
    @GetMapping("/potwierdzenie")
    public String potwierdzenie(@RequestParam("id") long id) throws DocumentException, FileNotFoundException {
        przelewyService.stworzPotwierdzeniePDF(id);
        return "redirect:/historiaOper";
    }
}
