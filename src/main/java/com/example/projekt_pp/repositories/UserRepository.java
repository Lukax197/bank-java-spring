package com.example.projekt_pp.repositories;

import com.example.projekt_pp.models.Kategorie;
import com.example.projekt_pp.models.Role;
import com.example.projekt_pp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.*;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername (String username);

    @Modifying
    @Query("update User u set u.id=:newId where u.username=:username")
    void editId(String username, Long newId);
}
