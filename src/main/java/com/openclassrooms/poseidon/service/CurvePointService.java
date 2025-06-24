package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.CurvePoint;
import jakarta.validation.Valid;

import java.util.List;

public interface CurvePointService {


    List<CurvePoint> getAllCurvePoints();


    CurvePoint createCurvePoint(@Valid CurvePoint curvePoint);


    CurvePoint getCurvePointById(Integer id);


    CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint);


    boolean deleteCurvePoint(Integer id);

}
