/*
package com.openclassrooms.poseidon.security;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Tentative ds loadUserByUsername : {}", username);

        User user = userRepository.findByUsername(username);

        if (user == null ) {
            log.error("Utilisateur non trouvé avec l'email : {}", username);
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le username : " + username);
        }
        log.info("Utilisateur trouvé : {}", username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantAuthorities(user.getRole())
        );
    }

    private List<GrantedAuthority> getGrantAuthorities(String role) {

        List<GrantedAuthority> auth = new ArrayList<>();

        auth.add(new SimpleGrantedAuthority(("ROLE_USER")));

        if (role.equals("ADMIN")) {
              auth.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return auth;
    }
}
*/
