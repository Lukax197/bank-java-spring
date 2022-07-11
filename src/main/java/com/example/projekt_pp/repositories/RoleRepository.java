package com.example.projekt_pp.repositories;

import com.example.projekt_pp.models.Role;
import com.example.projekt_pp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByType(Role.Types role);

    /*@Modifying
    @Query("update Role r set r.users=:u where r.type=:type")
    void editUsers(Role.Types type, Set<User> u);*/
}
