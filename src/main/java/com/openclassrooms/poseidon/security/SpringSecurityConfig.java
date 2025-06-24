package com.openclassrooms.poseidon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
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


    private final CustomUserDetailsService customUserDetailsService;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Mot de passe basé sur Bcrypt
     * Il est utilisé pour encoder et vérifier le mot de passe des users
     *
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


    /**
     * Méthode qui configure les règles de sécurité HTTP, notamment l'authentification,
     * l'autorisation, la gestion de la session ainsi que la page login
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests( authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login", "/css/**","/images/**", "/static/**").permitAll()
                                .requestMatchers("/trade/**", "/rating/**", "/ruleName/**",
                                        "/bidList/**", "/curvePoint/**").authenticated()
                                .requestMatchers("/user/**").hasRole("ADMIN")
                                .requestMatchers("/**").hasAnyRole("USER","ADMIN")
                                //.requestMatchers("/admin/**", "/user/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                        )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/trade/list", true)
                                .failureUrl("/login?error=true")
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll   // méthode de référence à la place de la lambda
                )
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                ;

        return http.build();
    }


    /**
     * Configure l'authenticationmanager avec customUserDetailsService ainsi que le passwordEncoder
     * @param http
     * @param bCryptPasswordEncoder
     * @return l'authenticationManager configuré
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

}
