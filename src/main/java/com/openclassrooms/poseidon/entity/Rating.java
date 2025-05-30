package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

// toString pour sécurité

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"orderNumber"})
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating")
    private Integer id;

    @NotBlank(message = "Le champ Moody's Rating ne peut pas être vide")
    private String moodysRating;
    @NotBlank(message = "Le champ S&P Rating ne peut pas être vide")
    private String sandPRating;
    @NotBlank(message = "Le champ Fitch Rating ne peut pas être vide")
    private String fitchRating;
    @PositiveOrZero(message = "Le numéro d'ordre doit être positif ou null")
    private Integer orderNumber;


}
