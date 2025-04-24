package com.openclassrooms.poseidon.service.serviceImpl;


import com.openclassrooms.poseidon.entity.Rating;
import com.openclassrooms.poseidon.entity.Trade;
import com.openclassrooms.poseidon.exception.RatingNotFoundException;
import com.openclassrooms.poseidon.exception.TradeNotFoundException;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import com.openclassrooms.poseidon.service.RatingService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private static final Logger log = LoggerFactory.getLogger(RatingServiceImpl.class);


    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }




    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Transactional
    @Override
    public Rating createRating(Rating rating) {
        log.info("Création d'une nouvelle notation : {}", rating);
        return ratingRepository.save(rating);
    }

    @Override
    public Rating getRatingById(Integer id) {
        log.info("Récupération de l'id : {}", id);
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("La notation n'a pas été trouvée pour l'id : " + id));
    }

    @Transactional
    @Override
    public Rating updateRating(Integer id, Rating rating) {

        Rating existingRating = getRatingById(id);
        existingRating.setMoodysRating(rating.getMoodysRating());
        existingRating.setSandPRating(rating.getSandPRating());
        existingRating.setFitchRating(rating.getFitchRating());
        // existingRating.setOrderNumber(rating.getOrderNumber());  // à voir

        log.info("Mise à jour de la notation : id={} avec les nouvelles données rating=[{}]", id, existingRating);
        // ensemble de l'objet rating dans les log ou bien par champs non sensibles ? Meilleure pratique ?
        return ratingRepository.save(existingRating);
    }

    @Transactional
    @Override
    public boolean deleteRating(Integer id) {
        Rating rating = getRatingById(id);
        ratingRepository.delete(rating);
        log.info("Suppression de la notation : {}", id);
        return true;
    }




}
