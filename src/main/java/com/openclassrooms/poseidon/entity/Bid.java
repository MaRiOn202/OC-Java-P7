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
//@ToString  pb champs sensibles
@Entity
@Table(name = "bidlist")
public class Bid {
    // TODO: Map columns in data table BIDLIST with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bidListId")
    private Integer bidListId;

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
    @NotBlank(message = "La date est obligatoire")
    private Timestamp bidListDate;
    @Size(max = 255, message = "Le commentaire ne doit pas dépasser 255 caractères")
    private String commentary;
    private String security;
    private String status;
    private String trader;
    private String book;
    @Size(max = 50)
    private String creationName;
    @NotBlank(message = "La date est obligatoire")
    private Timestamp creationDate;
    @Size(max = 50)
    private String revisionName;
    private Timestamp revisionDate;
    private String dealName;
    private String dealType;
    private String sourceListId;
    private String side;
    

    @Override
    public String toString() {
        return "BidList{" +
                "bidListId=" + bidListId +
                ", type='" + type + '\'' +
                ", bidQuantity=" + bidQuantity +
                ", askQuantity=" + askQuantity +
                ", bid=" + bid +
                ", ask=" + ask +
                ", benchmark='" + benchmark + '\'' +
                ", bidListDate=" + bidListDate +
                ", commentary='" + commentary + '\'' +
                ", status='" + status + '\'' +
                ", book='" + book + '\'' +
                ", creationName='" + creationName + '\'' +
                ", creationDate=" + creationDate +
                ", revisionName='" + revisionName + '\'' +
                ", revisionDate=" + revisionDate +
                ", dealName='" + dealName + '\'' +
                ", dealType='" + dealType + '\'' +
                ", sourceListId='" + sourceListId + '\'' +
                ", side='" + side + '\'' +
                '}';
    }
}
