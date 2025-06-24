package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.RuleName;
import com.openclassrooms.poseidon.exception.RuleNameNotFoundException;
import com.openclassrooms.poseidon.repositories.RuleNameRepository;
import com.openclassrooms.poseidon.service.serviceImpl.RuleNameServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleNameServiceImplTest {


    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameServiceImpl ruleNameService;


    @Test
    void getAllRuleNamesShouldReturnList() {

        List<RuleName> ruleNameList = Arrays.asList(new RuleName(), new RuleName());

        when(ruleNameRepository.findAll()).thenReturn(ruleNameList);

        List<RuleName> result = ruleNameService.getAllRuleNames();

        assertEquals(ruleNameList, result);
        verify(ruleNameRepository, times(1)).findAll();


    }

    @Test
    void getRuleNameByIdCasPassant() {

        int id = 1;
        RuleName ruleName = new RuleName();

        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(ruleName));

        RuleName result = ruleNameService.getRuleNameById(id);

        assertEquals(ruleName, result);
    }

    @Test
    void getRuleNameByIdCasNonPassant() {

        int id = 2;
        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(RuleNameNotFoundException.class, () -> {
            ruleNameService.getRuleNameById(id);
        });
        assertEquals("La règle n'a pas été trouvée : " + id, e.getMessage());


    }

    @Test
    void createRuleNameSuccess() {

        RuleName newRuleName = new RuleName();
        newRuleName.setName("Test1");
        newRuleName.setDescription("Ceci est une description");
        newRuleName.setJson("Test2");
        newRuleName.setTemplate("TEST");
        newRuleName.setSqlStr("Test3");
        newRuleName.setSqlPart("TEST4");


        RuleName ruleNameInBdd = new RuleName();
        ruleNameInBdd.setId(1);
        ruleNameInBdd.setName("Test1Modifié");
        ruleNameInBdd.setDescription("Ceci est une description");
        ruleNameInBdd.setTemplate("Test2Modifié");
        ruleNameInBdd.setSqlStr("Test3Modifié");
        ruleNameInBdd.setSqlPart("TEST4Modifié");

        when(ruleNameRepository.save(newRuleName)).thenReturn(ruleNameInBdd);

        RuleName result = ruleNameService.createRuleName(newRuleName);

        assertEquals(ruleNameInBdd.getName(), result.getName());
        assertEquals(ruleNameInBdd.getId(), result.getId());
        assertEquals(ruleNameInBdd.getDescription(), result.getDescription());
        assertEquals(ruleNameInBdd.getTemplate(), result.getTemplate());
        assertEquals(ruleNameInBdd.getSqlStr(), result.getSqlStr());
        assertEquals(ruleNameInBdd.getSqlPart(), result.getSqlPart());
        verify(ruleNameRepository, times(1)).save(newRuleName);
    }


    @Test
    void updateRuleNameShouldReturnAnUpdatedRuleName() {

        int id = 1;
        RuleName existingRuleName = new RuleName();
        existingRuleName.setId(id);
        existingRuleName.setName("Nom");
        existingRuleName.setDescription("Ceci est une description");
        existingRuleName.setJson("Json");
        existingRuleName.setTemplate("Template");
        existingRuleName.setSqlStr("Test3");
        existingRuleName.setSqlPart("TEST4");

        RuleName updatedRuleName = new RuleName();
        updatedRuleName.setId(id);
        updatedRuleName.setName("NomModifié");
        updatedRuleName.setDescription("Ceci est une description");
        updatedRuleName.setJson("JsonModifié");
        updatedRuleName.setTemplate("TemplateModifié");
        updatedRuleName.setSqlStr("Test3Modifié");
        updatedRuleName.setSqlPart("TEST4Modifié");

        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(existingRuleName));
        when(ruleNameRepository.save(any(RuleName.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RuleName resultInBdd = ruleNameService.updateRuleName(id, updatedRuleName);

        assertEquals("NomModifié", resultInBdd.getName());
        assertEquals("Ceci est une description", resultInBdd.getDescription());
        assertEquals("TemplateModifié", resultInBdd.getTemplate());
        assertEquals("JsonModifié", resultInBdd.getJson());
        assertEquals("Test3Modifié", resultInBdd.getSqlStr());
        verify(ruleNameRepository, times(1)).findById(id);
        verify(ruleNameRepository, times(1)).save(existingRuleName);

    }

    @Test
    void deleteRuleNameReturnTrue() {

        int id = 1;
        RuleName existingRating = new RuleName();
        existingRating.setId(id);

        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(existingRating));
        doNothing().when(ruleNameRepository).delete(existingRating);

        boolean result = ruleNameService.deleteRuleName(id);

        assertTrue(result);
        verify(ruleNameRepository, times(1)).findById(id);
        verify(ruleNameRepository, times(1)).delete(existingRating);

    }

    @Test
    public void deleteRatingShouldThrowAnExceptionWhenRatingNotFound() {

        Integer id = 9;
        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(RuleNameNotFoundException.class, () -> {
            ruleNameService.deleteRuleName(id);
        });

        assertEquals("La règle n'a pas été trouvée : " + id, e.getMessage());

        verify(ruleNameRepository, times(1)).findById(id);

    }

















}
