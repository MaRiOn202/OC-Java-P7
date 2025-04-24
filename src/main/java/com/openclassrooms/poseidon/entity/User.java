package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


//@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
  //  @GeneratedValue(strategy= GenerationType.AUTO)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "userId")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
