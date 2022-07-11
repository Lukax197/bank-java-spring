package com.example.projekt_pp.services;

import com.example.projekt_pp.configuration.ProfileNames;
import com.example.projekt_pp.models.Role;
import com.example.projekt_pp.repositories.RoleRepository;
import com.example.projekt_pp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userDetailsService")
@Profile(ProfileNames.USERS_IN_DATABASE)
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return convertToUserDetails(user);
    }

    private UserDetails convertToUserDetails(com.example.projekt_pp.models.User user) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getType().toString()));
        }

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);//UWAGA: klasa ma też drugi konstruktor – więcej parametrów!!!
    }

    @Override
    public boolean checkUsernames(com.example.projekt_pp.models.User user){
        List<com.example.projekt_pp.models.User> users=userRepository.findAll();
        for(int i=0;i<users.size();i++){
            if(user.getUsername().equals(users.get(i).getUsername())){
                return true;
            }
        }

        return false;
    }

    @PreAuthorize("isAnonymous() or isAuthenticated()")
    @Override
    public void saveUser(com.example.projekt_pp.models.User user){

        var userRole =roleRepository.findByType(Role.Types.ROLE_USER);
        var roles =new HashSet<Role>();
        roles.add(userRole);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordConfirm(user.getPassword());
        user.setEnabled(true);
        userRepository.save(user);
    }

    public com.example.projekt_pp.models.User getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
