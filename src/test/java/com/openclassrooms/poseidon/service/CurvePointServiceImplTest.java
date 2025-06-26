package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.CurvePoint;
import com.openclassrooms.poseidon.exception.CurvePointNotFoundException;
import com.openclassrooms.poseidon.repository.CurvePointRepository;
import com.openclassrooms.poseidon.service.serviceImpl.CurvePointServiceImpl;
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
public class CurvePointServiceImplTest {


    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointServiceImpl curvePointService;




    @Test
    void getAllCurvePointsShouldReturnList() {

        List<CurvePoint> curvePointList = Arrays.asList(new CurvePoint(), new CurvePoint());

        when(curvePointRepository.findAll()).thenReturn(curvePointList);

        List<CurvePoint> result = curvePointService.getAllCurvePoints();

        assertEquals(curvePointList, result);
        verify(curvePointRepository, times(1)).findAll();


    }


    @Test
    void getCurvePointByIdCasPassant() {

        int id = 1;
        CurvePoint curvePoint = new CurvePoint();

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(curvePoint));

        CurvePoint result = curvePointService.getCurvePointById(id);

        assertEquals(curvePoint, result);
    }

    @Test
    void getCurvePointByIdCasNonPassant() {

        int id = 2;
        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(CurvePointNotFoundException.class, () -> {
            curvePointService.getCurvePointById(id);
        });
        assertEquals("Le point de courbe n'a pas été trouvé : " + id, e.getMessage());


    }

    @Test
    void createCurvePointSuccess() {

        CurvePoint newCurve = new CurvePoint();
        newCurve.setTerm(12.0);
        newCurve.setValue(10.0);
        //newBid.setType("TypeN1");

        CurvePoint curveInBdd = new CurvePoint();
        curveInBdd.setId(1);
        curveInBdd.setTerm(22.0);
        curveInBdd.setValue(55.0);
        //bidInBdd.setType("TypeN1");

        when(curvePointRepository.save(newCurve)).thenReturn(curveInBdd);

        CurvePoint result = curvePointService.createCurvePoint(newCurve);

        assertEquals(curveInBdd.getTerm(), result.getTerm());
        assertEquals(curveInBdd.getId(), result.getId());
        assertEquals(curveInBdd.getValue(), result.getValue());
        verify(curvePointRepository, times(1)).save(newCurve);
    }

    @Test
    void updateCurvePointShouldReturnAnUpdatedCurve() {

        int id = 1;
        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setId(id);
        existingCurvePoint.setTerm(10.0);
        existingCurvePoint.setValue(50.0);

        CurvePoint updatedCurvePoint= new CurvePoint();
        updatedCurvePoint.setId(id);
        updatedCurvePoint.setTerm(25.0);
        updatedCurvePoint.setValue(120.0);

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(existingCurvePoint));
        when(curvePointRepository.save(any(CurvePoint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CurvePoint resultInBdd = curvePointService.updateCurvePoint(id, updatedCurvePoint);

        assertEquals(25.0, resultInBdd.getTerm());
        assertEquals(120.0, resultInBdd.getValue());
        verify(curvePointRepository, times(1)).findById(id);
        verify(curvePointRepository, times(1)).save(existingCurvePoint);

    }

    @Test
    void deleteCurvePointReturnTrue() {

        int id = 1;
        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setId(id);

        when(curvePointRepository.findById(id)).thenReturn(Optional.of(existingCurvePoint));
        doNothing().when(curvePointRepository).delete(existingCurvePoint);

        boolean result = curvePointService.deleteCurvePoint(id);

        assertTrue(result);
        verify(curvePointRepository, times(1)).findById(id);
        verify(curvePointRepository, times(1)).delete(existingCurvePoint);

    }


    @Test
    public void deleteCurvePointShouldThrowAnExceptionWhenCurvePointNotFound() {

        Integer id = 9;
        when(curvePointRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(CurvePointNotFoundException.class, () -> {
            curvePointService.deleteCurvePoint(id);
        });

        assertEquals("Le point de courbe n'a pas été trouvé : " + id, e.getMessage());

        verify(curvePointRepository, times(1)).findById(id);

    }















}
