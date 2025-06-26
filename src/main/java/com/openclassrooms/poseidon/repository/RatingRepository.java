package com.openclassrooms.poseidon.repository;

import com.openclassrooms.poseidon.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
