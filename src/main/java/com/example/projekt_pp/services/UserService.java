package com.example.projekt_pp.services;

import com.example.projekt_pp.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(User userForm);
    boolean checkUsernames(User user);
}
