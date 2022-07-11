package com.example.projekt_pp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 36)
    private String username;

    @NotBlank
    private String password;

    @Transient//właściwość nie będzie odzwierciedlona w db
    private String passwordConfirm;

    private String email;

    private boolean enabled = false;//czy konto jest aktywne

    public User(String username, String password, Set<GrantedAuthority> grantedAuthorities) {
    }

    public boolean isPasswordEquals(){
        if(password==null|| passwordConfirm==null)
            return false;
        else
            return password.equals(passwordConfirm);
    }

    //@ManyToMany
    //@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ManyToMany(fetch=FetchType.EAGER)
    private Set<Role> roles;

    public User(String username){
        this(username, false);
    }

    public User(String username, boolean enabled){
        this.username = username;
        this.enabled = enabled;
    }

    public User(String username, String email, String password, String passwordConfirm, boolean enabled){
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.enabled = enabled;
    }
}
