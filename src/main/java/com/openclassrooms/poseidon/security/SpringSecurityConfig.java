package com.openclassrooms.poseidon.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Classe de configuration de la sécurité Spring security
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {


    private final CustomUserDetailsService customUserDetailsService;

    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * Mot de passe basé sur l'algorithme Bcrypt
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
     * @return uen instance de securityFilterChain
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
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))     // session-based
                ;

        return http.build();
    }


    /**
     * Configure l'authenticationManager avec customUserDetailsService ainsi que le passwordEncoder
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
