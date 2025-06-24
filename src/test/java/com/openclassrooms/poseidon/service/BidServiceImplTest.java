package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.Bid;
import com.openclassrooms.poseidon.exception.BidNotFoundException;
import com.openclassrooms.poseidon.repositories.BidRepository;
import com.openclassrooms.poseidon.service.serviceImpl.BidServiceImpl;
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
public class BidServiceImplTest {

    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private BidServiceImpl bidService;




    @Test
    void getAllBidsShouldReturnList() {

        List<Bid> bidList = Arrays.asList(new Bid(), new Bid());

        when(bidRepository.findAll()).thenReturn(bidList);

        List<Bid> result = bidService.getAllBids();

        assertEquals(bidList, result);
        verify(bidRepository, times(1)).findAll();


    }

    @Test
    void getBidByIdCasPassant() {

        int id = 1;
        Bid bid = new Bid();

        when(bidRepository.findById(id)).thenReturn(Optional.of(bid));

        Bid result = bidService.getBidById(id);

        assertEquals(bid, result);
    }

    @Test
    void getBidByIdCasNonPassant() {

      int id = 2;
      when(bidRepository.findById(id)).thenReturn(Optional.empty());

      Exception e = assertThrows(BidNotFoundException.class, () -> {
          bidService.getBidById(id);
      });
      assertEquals("L'offre n'a pas été trouvée : " + id, e.getMessage());


    }

    @Test
    void createBidSuccess() {

        Bid newBid = new Bid();
        newBid.setAccount("AccountNb1");
        newBid.setBidQuantity(10.0);
        newBid.setType("TypeN1");

        Bid bidInBdd = new Bid();
        bidInBdd.setId(1);
        bidInBdd.setAccount("AccountNb1");
        bidInBdd.setBidQuantity(10.0);
        bidInBdd.setType("TypeN1");

        when(bidRepository.save(newBid)).thenReturn(bidInBdd);

        Bid result = bidService.createBid(newBid);

        assertEquals(bidInBdd.getAccount(), result.getAccount());
        assertEquals(bidInBdd.getId(), result.getId());
        assertEquals(bidInBdd.getBidQuantity(), result.getBidQuantity());
        verify(bidRepository, times(1)).save(newBid);
    }

    @Test
    void updateBidShouldReturnAnUpdatedBid() {

        int id = 1;
        Bid existingBid = new Bid();
        existingBid.setId(id);
        existingBid.setAccount("ExistingAccount");
        existingBid.setType("ExistingType");
        existingBid.setBidQuantity(10.0);

        Bid updatedBid = new Bid();
        updatedBid.setId(id);
        updatedBid.setAccount("AccountMaj");
        updatedBid.setType("TypeMaj");
        updatedBid.setBidQuantity(50.0);

        when(bidRepository.findById(id)).thenReturn(Optional.of(existingBid));
        when(bidRepository.save(any(Bid.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Bid resultInBdd = bidService.updateBid(id, updatedBid);

        assertEquals("AccountMaj", resultInBdd.getAccount());
        assertEquals("TypeMaj", resultInBdd.getType());
        assertEquals(50.0, resultInBdd.getBidQuantity());
        verify(bidRepository, times(1)).findById(id);
        verify(bidRepository, times(1)).save(existingBid);

    }

    @Test
    void deleteBidReturnTrue() {

        int id = 1;
        Bid existingBid = new Bid();
        existingBid.setId(id);

        when(bidRepository.findById(id)).thenReturn(Optional.of(existingBid));
        doNothing().when(bidRepository).delete(existingBid);

        boolean result = bidService.deleteBid(id);

        assertTrue(result);
        verify(bidRepository, times(1)).findById(id);
        verify(bidRepository, times(1)).delete(existingBid);

    }


    @Test
    public void deleteBidShouldThrowExceptionWhenBidNotFound() {

        Integer id = 9;
        when(bidRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(BidNotFoundException.class, () -> {
            bidService.deleteBid(id);
        });

        assertEquals("L'offre n'a pas été trouvée : " + id, e.getMessage());

        verify(bidRepository, times(1)).findById(id);

    }
















}
