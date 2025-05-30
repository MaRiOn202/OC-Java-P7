package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
  //  @GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @NotBlank(message = "Le username est obligatoire")
    private String username;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @ToString.Exclude
    private String password;
    @NotBlank(message = "Le nom complet est obligatoire")
    private String fullname;
    @NotBlank(message = "Le rôle est obligatoire")
    private String role;


}
