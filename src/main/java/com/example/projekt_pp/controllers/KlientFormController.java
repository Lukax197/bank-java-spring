package com.example.projekt_pp.controllers;

import com.example.projekt_pp.PasswordValidator;
import com.example.projekt_pp.configuration.ProfileNames;
import com.example.projekt_pp.models.Kategorie;
import com.example.projekt_pp.models.Klienci;
import com.example.projekt_pp.models.Uslugi;
import com.example.projekt_pp.repositories.ZalacznikiRepository;
import com.example.projekt_pp.services.KategorieService;
import com.example.projekt_pp.services.KlienciService;
import com.example.projekt_pp.services.KontaService;
import com.example.projekt_pp.services.UslugiService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@Profile(ProfileNames.NORMAL_CONTROLLER)
public class KlientFormController {

    private KategorieService kategorieService;
    private KlienciService klienciService;
    private UslugiService uslugiService;
    private KontaService kontaService;

    @ModelAttribute("kategorie")
    public List<Kategorie> loadKategorie(){
        return kategorieService.getAll();
    }

    @ModelAttribute("uslugi")
    public List<Uslugi> loadUslugi(){
        return uslugiService.getAll();
    }

    @GetMapping({"/add", "/edit"})
    public String showForm(Model m, @RequestParam(value = "id", required = false) Long id){

        if(id != null) {
            m.addAttribute("klient", klienciService.getKlient(id));
        }
        else {
            m.addAttribute("klient", new Klienci());
        }

        return "klientForm";
    }

    @PostMapping({"/add", "/edit"})
    public String processForm(@ModelAttribute("klient") @Valid Klienci klient, BindingResult result) throws MessagingException {

        if(result.hasErrors() || !klient.getInternetBankingAccount().isPasswordEquals()) {
            return "klientForm";
        }

        if(klient.getId() == 0) {
            klienciService.dodajKlienta(klient);
            return "redirect:/login";
        }
        else {
            klienciService.saveEditedKlient(klient);
            return "redirect:/klientList";
        }
    }

    @InitBinder("klient.internetBankingAccount")
    public void initBinder(WebDataBinder binder){
        binder.addValidators(new PasswordValidator());
        binder.setDisallowedFields("enabled");
    }
}
