package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.sql.Timestamp;

 // penser au toString attention aux champs sensible security 


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"security", "trader", "book"})
@Table(name = "trade")
public class
Trade {
    // TODO: Map columns in data table TRADE with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tradeId")

    private Integer tradeId;
    @NotBlank(message = "Le champ Account doit être complété")
    @Size(max = 50, message = "Le champ Account ne doit pas dépasser 50 caractères")
    private String account;
    @NotBlank(message = "Le champ Type doit être complété")
    private String type;
    @PositiveOrZero(message = "La quantité doit être positive ou nulle")
    private Double buyQuantity;
    private Double sellQuantity;
    @NotNull
    @DecimalMin(value = "0.0", message = "Le prix d'achat doit être positif ou nul")
    private Double buyPrice;

    private Double sellPrice;
    private String benchmark;
    //@Temporal(TemporalType.TIMESTAMP) // à voir
    @NotBlank(message = "La date est obligatoire")
    private Timestamp tradeDate;
    private String security;
    @NotBlank(message = "Le statut est obligatoire")
    private String status;
    @NotBlank(message = "Le trader est obligatoire")
    private String trader;
    private String book;
    @Size(max = 50)
    private String creationName;
    private Timestamp creationDate;
    @Size(max = 50)
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;


}
