package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.Trade;
import com.openclassrooms.poseidon.exception.TradeNotFoundException;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import com.openclassrooms.poseidon.service.serviceImpl.TradeServiceImpl;
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
public class TradeServiceImplTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeServiceImpl tradeService;


    @Test
    void getAllTradesShouldReturnList() {

        List<Trade> tradeList = Arrays.asList(new Trade(), new Trade());

        when(tradeRepository.findAll()).thenReturn(tradeList);

        List<Trade> result = tradeService.getAllTrades();

        assertEquals(tradeList, result);
        verify(tradeRepository, times(1)).findAll();

    }

    @Test
    void getTradeByIdCasPassant() {

        int id = 1;
        Trade trade = new Trade();

        when(tradeRepository.findById(id)).thenReturn(Optional.of(trade));

        Trade result = tradeService.getTradeById(id);

        assertEquals(trade, result);
    }

    @Test
    void getTradeByIdCasNonPassant() {

        int id = 2;
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(TradeNotFoundException.class, () -> {
            tradeService.getTradeById(id);
        });
        assertEquals("Le trade n'a pas été trouvé : " + id, e.getMessage());

    }

    @Test
    void createTradeSuccess() {

        Trade newTrade = new Trade();
        newTrade.setAccount("Compte");
        newTrade.setType("Type");
        newTrade.setBuyQuantity(32.0);
        newTrade.setBuyPrice(55.00);
        //newTrade.setSqlStr("Test3");
        //newTrade.setSqlPart("TEST4");


        Trade tradeInBdd = new Trade();
        tradeInBdd.setId(1);
        tradeInBdd.setAccount("Compte2");
        tradeInBdd.setType("Type2");
        tradeInBdd.setBuyQuantity(56.0);
        tradeInBdd.setBuyPrice(78.0);
       // tradeInBdd.setSqlPart("TEST4Modifié");

        when(tradeRepository.save(newTrade)).thenReturn(tradeInBdd);

        Trade result = tradeService.createTrade(newTrade);

        assertEquals(tradeInBdd.getAccount(), result.getAccount());
        assertEquals(tradeInBdd.getId(), result.getId());
        assertEquals(tradeInBdd.getType(), result.getType());
        assertEquals(tradeInBdd.getBuyQuantity(), result.getBuyQuantity());
        assertEquals(tradeInBdd.getBuyPrice(), result.getBuyPrice());
        // assertEquals(tradeInBdd.getSqlPart(), result.getSqlPart());
        verify(tradeRepository, times(1)).save(newTrade);
    }

    @Test
    void updateTradeShouldReturnAnUpdatedTrade() {

        int id = 1;
        Trade existingTrade = new Trade();
        existingTrade.setId(id);
        existingTrade.setAccount("Compte");
        existingTrade.setType("Type");
        existingTrade.setBuyQuantity(32.0);
        existingTrade.setBuyPrice(55.00);
        //existingRuleName.setSqlStr("Test3");
        //existingRuleName.setSqlPart("TEST4");

        Trade updatedTrade = new Trade();
        updatedTrade.setId(id);
        updatedTrade.setAccount("CompteModifié");
        updatedTrade.setType("TypeModifié");
        updatedTrade.setBuyQuantity(56.0);
        updatedTrade.setBuyPrice(78.0);
        //updatedRuleName.setSqlStr("Test3Modifié");
        //updatedRuleName.setSqlPart("TEST4Modifié");

        when(tradeRepository.findById(id)).thenReturn(Optional.of(existingTrade));
        when(tradeRepository.save(any(Trade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Trade resultInBdd = tradeService.updateTrade(id, updatedTrade);

        assertEquals("CompteModifié", resultInBdd.getAccount());
        assertEquals("TypeModifié", resultInBdd.getType());
        assertEquals(56.0, resultInBdd.getBuyQuantity());
        assertEquals(78.0, resultInBdd.getBuyPrice());
        //assertEquals("Test3Modifié", resultInBdd.getSqlStr());
        verify(tradeRepository, times(1)).findById(id);
        verify(tradeRepository, times(1)).save(existingTrade);

    }


    @Test
    void deleteTradeReturnTrue() {

        int id = 1;
        Trade existingTrade = new Trade();
        existingTrade.setId(id);

        when(tradeRepository.findById(id)).thenReturn(Optional.of(existingTrade));
        doNothing().when(tradeRepository).delete(existingTrade);

        boolean result = tradeService.deleteTrade(id);

        assertTrue(result);
        verify(tradeRepository, times(1)).findById(id);
        verify(tradeRepository, times(1)).delete(existingTrade);

    }

    @Test
    public void deleteTradeShouldThrowAnExceptionWhenTradeNotFound() {

        Integer id = 9;
        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(TradeNotFoundException.class, () -> {
            tradeService.deleteTrade(id);
        });

        assertEquals("Le trade n'a pas été trouvé : " + id, e.getMessage());

        verify(tradeRepository, times(1)).findById(id);

    }







}
