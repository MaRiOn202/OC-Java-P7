package com.openclassrooms.poseidon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.UUID;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

/*    @Autowired
    private CustomUserDetailsService customUserDetailsService;*/


    @Bean
    public static PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        //String generatedPassword = UUID.randomUUID().toString();
        String generatedPassword = "user";
        System.out.println("🛡️ Mot de passe généré (à copier-coller dans le login) : " + generatedPassword);

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode(generatedPassword))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
     // à faire plus tard avec un admin test 
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/static").permitAll()
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers("/bidList/**").permitAll()
                                .requestMatchers("/curvePoint/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/secure/**").permitAll()
                                .requestMatchers("/rating/**").permitAll()
                                .requestMatchers("/ruleName/**").permitAll()
                                .requestMatchers("/trade/**").permitAll()

                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .securityContext(securityContext -> securityContext.securityContextRepository(new HttpSessionSecurityContextRepository()
                ));
        return http.build();
    }

    @Bean
    public AuthenticationManager auth(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }*/


}
