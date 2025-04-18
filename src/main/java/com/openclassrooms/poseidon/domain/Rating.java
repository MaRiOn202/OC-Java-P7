package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Timestamp;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rating")
public class Rating {
    // TODO: Map columns in data table RATING with corresponding java fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating")
    private Integer id;

    private String moodysRating;
    private String sandPRating;
    private String fitchRating;
    private Integer orderNumber;


}
