package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.Rating;
import com.openclassrooms.poseidon.exception.RatingNotFoundException;
import com.openclassrooms.poseidon.repositories.RatingRepository;
import com.openclassrooms.poseidon.service.serviceImpl.RatingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RatingServiceImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;


    @Test
    void getAllRatingsShouldReturnList() {

        List<Rating> ratingList = Arrays.asList(new Rating(), new Rating());

        when(ratingRepository.findAll()).thenReturn(ratingList);

        List<Rating> result = ratingService.getAllRatings();

        assertEquals(ratingList, result);
        verify(ratingRepository, times(1)).findAll();


    }

    @Test
    void getRatingByIdCasPassant() {

        int id = 1;
        Rating rating = new Rating();

        when(ratingRepository.findById(id)).thenReturn(Optional.of(rating));

        Rating result = ratingService.getRatingById(id);

        assertEquals(rating, result);
    }

    @Test
    void getRatingByIdCasNonPassant() {

        int id = 2;
        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(RatingNotFoundException.class, () -> {
            ratingService.getRatingById(id);
        });
        assertEquals("La notation n'a pas été trouvée pour l'id : " + id, e.getMessage());


    }

    @Test
    void createRatingSuccess() {

        Rating newRating = new Rating();
        newRating.setMoodysRating("Test1");
        newRating.setSandPRating("Test2");
        newRating.setFitchRating("Test3");
        newRating.setOrderNumber(25);


        Rating ratingInBdd = new Rating();
        ratingInBdd.setId(1);
        ratingInBdd.setMoodysRating("Test1Modifié");
        ratingInBdd.setSandPRating("Test2Modifié");
        ratingInBdd.setFitchRating("Test3Modifié");
        ratingInBdd.setOrderNumber(125);

        when(ratingRepository.save(newRating)).thenReturn(ratingInBdd);

        Rating result = ratingService.createRating(newRating);

        assertEquals(ratingInBdd.getMoodysRating(), result.getMoodysRating());
        assertEquals(ratingInBdd.getId(), result.getId());
        assertEquals(ratingInBdd.getSandPRating(), result.getSandPRating());
        assertEquals(ratingInBdd.getFitchRating(), result.getFitchRating());
        assertEquals(ratingInBdd.getOrderNumber(), result.getOrderNumber());
        verify(ratingRepository, times(1)).save(newRating);
    }


    @Test
    void updateRatingShouldReturnAnUpdatedRating() {

        int id = 1;
        Rating existingRating = new Rating();
        existingRating.setId(id);
        existingRating.setMoodysRating("Test1");
        existingRating.setSandPRating("Test2");
        existingRating.setFitchRating("Test3");
        existingRating.setOrderNumber(32);

        Rating updatedRating = new Rating();
        updatedRating.setId(id);
        updatedRating.setMoodysRating("Test1Modifié1");
        updatedRating.setSandPRating("Test1Modifié2");
        updatedRating.setFitchRating("Test1Modifié3");
        updatedRating.setOrderNumber(152);

        when(ratingRepository.findById(id)).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(any(Rating.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Rating resultInBdd = ratingService.updateRating(id, updatedRating);

        assertEquals("Test1Modifié1", resultInBdd.getMoodysRating());
        assertEquals("Test1Modifié2", resultInBdd.getSandPRating());
        assertEquals("Test1Modifié3", resultInBdd.getFitchRating());
        assertEquals(152, resultInBdd.getOrderNumber());
        verify(ratingRepository, times(1)).findById(id);
        verify(ratingRepository, times(1)).save(existingRating);

    }

    @Test
    void deleteRatingReturnTrue() {

        int id = 1;
        Rating existingRating = new Rating();
        existingRating.setId(id);

        when(ratingRepository.findById(id)).thenReturn(Optional.of(existingRating));
        doNothing().when(ratingRepository).delete(existingRating);

        boolean result = ratingService.deleteRating(id);

        assertTrue(result);
        verify(ratingRepository, times(1)).findById(id);
        verify(ratingRepository, times(1)).delete(existingRating);

    }

    @Test
    public void deleteRatingShouldThrowAnExceptionWhenRatingNotFound() {

        Integer id = 9;
        when(ratingRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(RatingNotFoundException.class, () -> {
            ratingService.deleteRating(id);
        });

        assertEquals("La notation n'a pas été trouvée pour l'id : " + id, e.getMessage());

        verify(ratingRepository, times(1)).findById(id);

    }







}
