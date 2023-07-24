package com.propulse.backendfacturier.security;

import com.propulse.backendfacturier.entity.User;
import com.propulse.backendfacturier.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /*
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.loadUserByEmail(email);
        if(user.isActive()){
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            });
            org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
            return userDetails;
        } else {
            if (user == null) throw new UsernameNotFoundException("User Not Found Or Your account is deactivated");
            return null;
        }

     */

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Vous pouvez déterminer si l'identifiant est un email ou un numéro de téléphone ici
        User user = null;
        if (isEmail(identifier)) {
            user = userService.loadUserByEmail(identifier);
        } else if (isPhoneNumber(identifier)) {
            user = userService.loadUserByPhoneNumber(identifier);
        }

        if (user != null && user.isActive()) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            });
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("User Not Found Or Your account is deactivated");
        }
    }

    private boolean isEmail(String identifier) {
        // Expression régulière pour valider un email
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Vérifie si l'identifiant correspond à l'expression régulière
        return identifier.matches(emailRegex);
    }

    // Méthode utilitaire pour vérifier si l'identifiant est un numéro de téléphone
    private boolean isPhoneNumber(String identifier) {
        // Expression régulière pour valider un numéro de téléphone
        // Ici, nous supposons que le numéro de téléphone est composé uniquement de chiffres
        String phoneRegex = "^[0-9]+$";

        // Vérifie si l'identifiant correspond à l'expression régulière
        return identifier.matches(phoneRegex);
    }

}
