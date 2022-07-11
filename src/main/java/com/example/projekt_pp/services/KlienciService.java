package com.example.projekt_pp.services;

import com.example.projekt_pp.filters.Filter;
import com.example.projekt_pp.models.*;
import com.example.projekt_pp.repositories.KlienciRepository;
import com.example.projekt_pp.repositories.KontaRepository;
import com.example.projekt_pp.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class KlienciService {

    private final KlienciRepository klienciRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final KontaRepository kontaRepository;
    private final Random gen = new Random();
    private PasswordEncoder passwordEncoder;

    public void dodajKlienta(Klienci klient) throws MessagingException {

        //klient.setId(getCount() + 1);
        Role roleUser = roleRepository.findByType(Role.Types.ROLE_USER);
        klient.getInternetBankingAccount().setPassword(passwordEncoder.encode(klient.getInternetBankingAccount().getPassword()));
        klient.getInternetBankingAccount().setRoles(new HashSet<>(Arrays.asList(roleUser)));
        klient.getInternetBankingAccount().setEnabled(true);

        klient.getKonto().setKlient(klient);
        klient.getKonto().setNr_konta(gen.nextInt(99999999)+1600000000);
        klient.getKonto().setPin(gen.nextInt(8999)+1000);
        klient.setAktywny(true);

        klienciRepository.save(klient);

        sendEmail(klient.getInternetBankingAccount().getEmail(), klient.getInternetBankingAccount().getUsername());
    }

    public void saveEditedKlient(Klienci klient) {
        klient.getInternetBankingAccount().setPassword(passwordEncoder.encode(klient.getInternetBankingAccount().getPassword()));
        klient.getInternetBankingAccount().setEnabled(true);
        System.out.println("Numer konta = " + klient.getKonto().getNr_konta());
        klienciRepository.save(klient);
    }

    public Long getCount() {return klienciRepository.count();}

    public Klienci getKlient(long id) {return klienciRepository.findById(Long.valueOf(id)).orElseThrow();}

    public void deleteKlient(long id) {klienciRepository.deleteById(id);}

    public List<Klienci> getAll() {return klienciRepository.findAll();}

    public Klienci getKlientByInternetBankingAccount(User u) {
        return klienciRepository.findByInternetBankingAccount(u);
    }

    public List<Klienci> searchFilter(Filter f) {
        if(f.kategoriaIsNull()){
            return klienciRepository.findKlienciFilter(f.getPhase().toUpperCase(Locale.ROOT), f.isActive());
        }
        else if(f.getPhase() == "") {
            return klienciRepository.findKlienciFilter(f.isActive(), f.getKategoria());
        }
        else {
            return klienciRepository.findKlienciFilter(f.getPhase().toUpperCase(Locale.ROOT), f.isActive(), f.getKategoria());
        }
    }

    @Async
    public void sendEmail(String to, String username) throws MessagingException {
        String emailText = "<div style=\"background-image: url(https://www.tapetus.pl/obrazki/n/234074_pieniadze-banknoty.jpg); height: 500px; width: 1200px; padding: 10px;\"> <center>" +
                "<br> <br><div style=\"background-color: #eee; padding: 10px; width: 800px;\">" +
                "<h1> Witaj " + username + ", </h1>" +
                "</div>" +
                "<br> <br> <br><div style=\"background-color: #eee; padding: 10px; width: 800px;\"> <center> <h1> Dziękujemy za wybranie naszego banku! </h1> <br/> " +
                "<h3> Mamy nadzieję, że świadczone przez nas usługi będą zadowoalające. </h3> " +
                "<br/> </br> </br> Pozdrawiamy, <br/> administracja Banku Horizon.</center> </div>" +
                "</center></div>";
        mailService.sendMail(to,
                "Witamy w Banku Horizon!",
                emailText, true);
    }
}
