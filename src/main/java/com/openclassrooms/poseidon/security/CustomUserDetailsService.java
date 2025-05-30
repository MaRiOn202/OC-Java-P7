package com.openclassrooms.poseidon.security;

import com.openclassrooms.poseidon.entity.User;
import com.openclassrooms.poseidon.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    private final UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Tentative ds loadUserByUsername : {}", username);

        User user = userRepository.findByUsername(username);

        if (user == null ) {
            log.error("Utilisateur non trouvé avec le username : {}", username);
            throw new UsernameNotFoundException("Utilisateur non trouvé avec le username : " + username);
        }
        log.info("Utilisateur trouvé : {}", username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getGrantAuthorities(user.getRole())
        );
    }

    // on convertit les rôles en authorities
    private List<GrantedAuthority> getGrantAuthorities(String role) {

        List<GrantedAuthority> auth = new ArrayList<>();

        auth.add(new SimpleGrantedAuthority(("ROLE_" + role)));

        return auth;
    }
}
