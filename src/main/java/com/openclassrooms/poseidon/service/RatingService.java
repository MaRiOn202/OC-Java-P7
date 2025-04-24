package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.Rating;


import java.util.List;

public interface RatingService {



    List<Rating> getAllRatings();


    Rating createRating(Rating rating);


    Rating getRatingById(Integer id);


    Rating updateRating(Integer id, Rating rating);


    boolean deleteRating(Integer id);
}
