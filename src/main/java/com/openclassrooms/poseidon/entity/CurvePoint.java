package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.sql.Timestamp;

// ToString security

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    // TODO: Map columns in data table CURVEPOINT with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "curvePointId")
    private Integer id;

    @NotNull(message = "CurveId ne peut pas être null")
    private Integer curveId;
    @NotNull(message = "AsOfDate ne peut pas être null")
    private Timestamp asOfDate;   // à la date
    @NotNull(message = "Le terme ne peut pas être null")
    @DecimalMin(value = "0.0", message = "Le terme doit être positif ou nul")
    private Double term;
    @DecimalMin(value = "0.0", message = "La valeur doit être positive ou nulle")
    private Double value;
    @NotNull(message = "La date de création ne peut pas être nulle")
    private Timestamp creationDate;

}
