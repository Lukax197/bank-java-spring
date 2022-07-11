package com.example.projekt_pp.configuration;

import com.example.projekt_pp.models.*;
import com.example.projekt_pp.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Configuration
public class RepositoriesInitializer {

    @Autowired
    private KlienciRepository klienci_repository;

    @Autowired
    private KontaRepository kontaRepository;

    @Autowired
    private UslugiRepository uslugiRepository;

    @Autowired
    private KategorieRepository kategorieRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    InitializingBean init() {

        return () -> {
            User test3 = null;

            if(roleRepository.findAll().isEmpty()){
                Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
                Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));

                User user = new User("user", true);
                user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                user.setPassword(passwordEncoder.encode("user"));
                user.setEmail("user@example.com");

                User admin = new User("admin", true);
                admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setEmail("admin@example.com");

                User test = new User("superuser", true);
                test.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
                test.setPassword(passwordEncoder.encode("superuser"));
                test.setEmail("superuser@example.com");

                User test2 = new User("test", true);
                test2.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                test2.setPassword(passwordEncoder.encode("test"));
                test2.setEmail("test@example.com");

                test3 = new User("test2", true);
                test3.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                test3.setPassword(passwordEncoder.encode("test2"));
                test3.setEmail("test2@example.com");

                userRepository.save(user);
                userRepository.save(admin);
                userRepository.save(test);
                userRepository.save(test2);
                userRepository.save(test3);
            }

            Kategorie kat1 = new Kategorie("Osoba fizyczna");
            Kategorie kat2 = new Kategorie("Firma");
            Kategorie kat3 = new Kategorie("Osoba młodociana");
            Kategorie kat4 = new Kategorie("Senior");

            if(kategorieRepository.findAll().isEmpty()) {
                kategorieRepository.save(kat1);
                kategorieRepository.save(kat2);
                kategorieRepository.save(kat3);
                kategorieRepository.save(kat4);
            }

            LocalDate date = LocalDate.of(1995, 5, 25);
            Klienci K1 = new Klienci(1,"Pan", "Kowalski","ul. Przyklad 4/5", date, kat1);
            date = LocalDate.of(1998, 10, 22);
            Klienci K2 = new Klienci(2,"Drugi", "Kowalski", "ul. Przyklad 5/4", date, kat2);
            date = LocalDate.of(1985, 7, 14);
            Klienci K3 = new Klienci(3,"Marian", "Tomasiewicz","ul. Chlebowa 7", date, kat1);
            date = LocalDate.of(2001, 3, 11);
            Klienci K4 = new Klienci(4,"Konrad", "Pawłowski", "ul. Adresowa 89/4", date, kat2);
            K4.setInternetBankingAccount(test3);

            if(klienci_repository.findAll().isEmpty()) {
                klienci_repository.save(K1);
                klienci_repository.save(K2);
                klienci_repository.save(K3);
                klienci_repository.save(K4);
            }

            Set<Uslugi> uslugi = new HashSet<>();

            if(uslugiRepository.findAll().isEmpty()) {
                Uslugi U1 = new Uslugi("płatności kartą");
                uslugiRepository.save(U1);
                Uslugi U2 = new Uslugi("przelewy premium 0zł");
                uslugiRepository.save(U2);
                Uslugi U3 = new Uslugi("powiadomienia sms");
                uslugiRepository.save(U3);
                uslugi.add(U1);
                uslugi.add(U2);
            }

            if(kontaRepository.findAll().isEmpty()) {
                Random gen = new Random();
                Konta Ko1 = new Konta(gen.nextInt(99999999)+1600000000, gen.nextInt(8999)+1000, K2);
                Ko1.setUslugi(uslugi);
                Konta Ko2 = new Konta(gen.nextInt(99999999)+1600000000, gen.nextInt(8999)+1000, K1);
                Ko2.setUslugi(uslugi);
                Konta Ko3 = new Konta(gen.nextInt(99999999)+1600000000, gen.nextInt(8999)+1000, K3);
                Ko3.setUslugi(uslugi);
                Konta Ko4 = new Konta(gen.nextInt(99999999)+1600000000, gen.nextInt(8999)+1000, K4);
                Ko4.setUslugi(uslugi);
                kontaRepository.save(Ko1);
                kontaRepository.save(Ko2);
                kontaRepository.save(Ko3);
                kontaRepository.save(Ko4);
            }
        };
    }
}

