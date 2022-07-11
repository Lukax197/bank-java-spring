package com.example.projekt_pp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Profile(ProfileNames.USERS_IN_MEMORY)
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        User.UserBuilder userBuilder = User.builder();

        UserDetails user = userBuilder
                .username("user")
                .password(passwordEncoder.encode("user"))
                .roles("USER")
                .build();

        UserDetails admin = userBuilder
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails superuser = userBuilder
                .username("superuser")
                .password(passwordEncoder.encode("superuser"))
                .roles("ADMIN","USER")
                .build();

        manager.createUser(user);
        manager.createUser(admin);
        manager.createUser(superuser);

        return manager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**", "/", "/layouts/**").permitAll()
                .antMatchers("/add/**", "/edit/**").hasRole("ADMIN")
                //.antMatchers("/add","/edit").hasRole("ADMIN")
                //.antMatchers("/login","/static/**", "/klientList","/logout","/details","/templates/**","/klienci").permitAll()
                //.antMatchers("/details").hasRole("USER")
                //.antMatchers("/edit","/add","/dodajKonto","/edytujKonto","/klientForm").hasRole("ADMIN")
                .anyRequest().authenticated();//każde żądanie ma być uwierzytelnione
        http
                .formLogin()//uwierzytelnienie poprzez formularz logowania
                .loginPage("/login")//formularz dostępny jest pod URL
                .defaultSuccessUrl("/",true)
                .permitAll();//przyznaj dostęp wszystkim użytkownikom
        http.exceptionHandling()
                .accessDeniedPage("/error403");

        http.logout().logoutSuccessUrl("/").permitAll();

        http.csrf().disable();
    }

}

