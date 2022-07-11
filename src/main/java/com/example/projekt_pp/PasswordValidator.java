package com.example.projekt_pp;

import com.example.projekt_pp.models.Klienci;
import com.example.projekt_pp.models.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordValidator implements Validator {

    @Override//Wspierana klasa
    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    @Override//Logika związana z poprawnością danych w obiekcie
    public void validate(Object o, Errors errors) {

        var something = (User) o;
        if (!something.isPasswordEquals()){
            errors.rejectValue("internetBankingAccount.password", "Negative.klient.internetBankingAccount.password");
        }
    }
}
