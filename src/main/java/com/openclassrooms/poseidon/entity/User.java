package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;


//@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotBlank(message = "Le username est obligatoire")
    private String username;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @ToString.Exclude
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$",
    message = "Le mot de passe doit contenir au moins une lettre majuscule, 8 caractères, un chiffre et un symbole")
    private String password;
    @NotBlank(message = "Le nom complet est obligatoire")
    private String fullname;
    @NotBlank(message = "Le rôle est obligatoire")
    private String role;


}
