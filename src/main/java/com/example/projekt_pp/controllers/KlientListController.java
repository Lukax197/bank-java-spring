package com.example.projekt_pp.controllers;

import com.example.projekt_pp.configuration.ProfileNames;
import com.example.projekt_pp.filters.Filter;
import com.example.projekt_pp.formatters.Formatter;
import com.example.projekt_pp.models.Kategorie;
import com.example.projekt_pp.models.Klienci;
import com.example.projekt_pp.models.User;
import com.example.projekt_pp.services.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;


@Controller
@AllArgsConstructor
@Profile(ProfileNames.NORMAL_CONTROLLER)
public class KlientListController {

    private KlienciService klienciService;
    private KategorieService kategorieService;
    private UserServiceImpl userService;
    private KontaService kontaService;

    @ModelAttribute("filter")
    public Filter loadFilter(){
        return new Filter();
    }

    @ModelAttribute("kategorie")
    public List<Kategorie> loadKategorie(){
        return kategorieService.getAll();
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/klientList")
    public String List(Model m){
        m.addAttribute("klient", klienciService.getAll());
        return "klientList";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/klientList", params={"did"})
    public String deleteKlient(long did) {
        klienciService.deleteKlient(did);
        return "redirect:/klientList";
    }

    @GetMapping("/details")
    public String showForm(Model m, @RequestParam(value="id", required=false) Long id) {

        if(id != null) {
            m.addAttribute("klient", klienciService.getKlient(id));
        }
        else{
            Klienci K = new Klienci();
            m.addAttribute("klient",K);
        }

        return "klientDetails";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/wyszukaj")
    public String search(Model m, @ModelAttribute("filter") Filter f) {
        m.addAttribute("klient", klienciService.searchFilter(f));
        Formatter fo = new Formatter();
        m.addAttribute("parameters", fo.print(f, Locale.ROOT));
        return "klientList";
    }

    @Secured("ROLE_USER")
    @GetMapping("/profil")
    public String getProfil(Model m, Authentication auth) {
        User u = userService.getUser(auth.getName());
        Klienci k = klienciService.getKlientByInternetBankingAccount(u);
        m.addAttribute("klient", k);
        return "profil";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(path="/wplac", params={"id"})
    public String getPage(Model m, long id) {
        m.addAttribute("id", id);
        return "wplataForm";
    }

    @PostMapping(path="/wplac", params={"id"})
    public String wplac(long id, @RequestParam("kwota") double kwota) {
        System.out.println(kwota);
        kontaService.edytujSaldo(id,kwota);
        return "redirect:/details?id="+id;
    }

}
