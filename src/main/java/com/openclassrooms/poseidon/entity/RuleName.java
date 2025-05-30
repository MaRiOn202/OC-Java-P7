package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"sqlStr", "sqlPart"})
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank(message = "Le nom ne peut pas être vide")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String name;
    @Size(max = 255, message = "La description ne peut pas dépasser 255 caractères")
    private String description;
    @Size(max = 500, message = "Le champ Json ne peut pas dépasser 500 caractères")
    private String json;
    @Size(max = 500, message = "Le champ Template ne peut pas dépasser 500 caractères")
    private String template;
    @Size(max = 500, message = "Le champ SQL String ne peut pas dépasser 500 caractères")
    private String sqlStr;
    @Size(max = 500, message = "Le champ SQL Part ne peut pas dépasser 500 caractères")
    private String sqlPart;


}
