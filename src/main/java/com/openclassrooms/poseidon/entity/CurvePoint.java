package com.openclassrooms.poseidon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.sql.Timestamp;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString(exclude = {"creationDate"})
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private Integer curveId;
    private Timestamp asOfDate;

    @NotNull(message = "Le terme ne peut pas être null")
    @DecimalMin(value = "0.0", message = "Le terme doit être positif ou nul")
    private Double term;
    @NotNull(message = "Le terme ne peut pas être null")
    @DecimalMin(value = "0.0", message = "La valeur doit être positive ou nulle")
    private Double value;

    //@NotNull(message = "La date de création ne peut pas être nulle")
    private Timestamp creationDate;

}
