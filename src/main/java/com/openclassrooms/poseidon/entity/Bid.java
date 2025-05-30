package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"commentary","creationDate"})
@Entity
@Table(name = "bidlist")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_list_id")
    private Integer id;

    @NotBlank(message = "Le champ Account doit être complété")
    private String account;
    @NotBlank(message = "Le champ Type doit être complété")
    private String type;
    @PositiveOrZero(message = "La quantité doit être positive ou nulle")
    private Double bidQuantity;
    @PositiveOrZero(message = "La quantité doit être positive ou nulle")
    private Double askQuantity;
    @DecimalMin(value = "0.0", message = "L'offre doit être positive")
    private Double bid;
    @DecimalMin(value = "0.0", message = "La demande doit être positive")
    private Double ask;
    private String benchmark;
    private Timestamp bidListDate;
    @Size(max = 255, message = "Le commentaire ne doit pas dépasser 255 caractères")
    private String commentary;
    private String security;
    private String status;
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
