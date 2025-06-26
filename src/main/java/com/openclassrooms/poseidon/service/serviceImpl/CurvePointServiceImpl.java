package com.openclassrooms.poseidon.service.serviceImpl;


import com.openclassrooms.poseidon.entity.CurvePoint;
import com.openclassrooms.poseidon.exception.CurvePointNotFoundException;
import com.openclassrooms.poseidon.repository.CurvePointRepository;
import com.openclassrooms.poseidon.service.CurvePointService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurvePointServiceImpl implements CurvePointService {

    private final CurvePointRepository curvePointRepository;

    private static final Logger log = LoggerFactory.getLogger(CurvePointServiceImpl.class);

    public CurvePointServiceImpl(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }




    @Override
    public List<CurvePoint> getAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    @Transactional
    @Override
    public CurvePoint createCurvePoint(CurvePoint curvePoint) {
        log.info("Création d'un nouveau point de courbe : {}", curvePoint);
        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint getCurvePointById(Integer id) {
        log.info("Récupération de l'id : {}", id);
        return  curvePointRepository.findById(id)
                .orElseThrow(() -> new CurvePointNotFoundException("Le point de courbe n'a pas été trouvé : " + id));
    }

    @Transactional
    @Override
    public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {

        CurvePoint existingCurvePoint = getCurvePointById(id);

        existingCurvePoint.setTerm(curvePoint.getTerm());
        existingCurvePoint.setValue(curvePoint.getValue());

        log.info("Mise à jour du point de courbe : id={}, avec les nouvelles données curvePoint=[{}]", id, existingCurvePoint);
        return curvePointRepository.save(existingCurvePoint);
    }

    @Transactional
    @Override
    public boolean deleteCurvePoint(Integer id) {
        CurvePoint curvePoint = getCurvePointById(id);
        curvePointRepository.delete(curvePoint);
        log.info("Suppression du curvePoint : {}", id);
        return true;
    }

}



