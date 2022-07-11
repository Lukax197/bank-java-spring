package com.example.projekt_pp.controllers;

import com.example.projekt_pp.models.Klienci;
import com.example.projekt_pp.models.Kontakty;
import com.example.projekt_pp.models.User;
import com.example.projekt_pp.services.KlienciService;
import com.example.projekt_pp.services.KontaktyService;
import com.example.projekt_pp.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class KontaktyController {

    private UserServiceImpl userService;
    private KlienciService klienciService;
    private KontaktyService kontaktyService;

    @Secured("ROLE_USER")
    @GetMapping("/kontakty")
    public String getKontaktyPage(Model m, Authentication auth){
        m.addAttribute("kontakt", new Kontakty());
        User u = userService.getUser(auth.getName());
        Klienci k = klienciService.getKlientByInternetBankingAccount(u);
        m.addAttribute("kontaktyList", kontaktyService.getKontakty(k.getId()));
        return "kontakty";
    }

    @Secured("ROLE_USER")
    @PostMapping("/kontakty")
    public String addKontakt(@ModelAttribute("kontakt") @Valid Kontakty kontakt, BindingResult result, Authentication auth){

        if(result.hasErrors()){
            return "kontakty";
        }

        User u = userService.getUser(auth.getName());
        Klienci k = klienciService.getKlientByInternetBankingAccount(u);
        kontakt.setK(k.getId());
        kontaktyService.addKontakt(kontakt);
        return "redirect:/kontakty";
    }
}
