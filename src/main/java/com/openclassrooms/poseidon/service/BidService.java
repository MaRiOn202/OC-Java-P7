package com.openclassrooms.poseidon.service;

import com.openclassrooms.poseidon.entity.Bid;

import java.util.List;

public interface BidService {


    List<Bid> getAllBids();

    Bid createBid(Bid bid);

    Bid getBidById(Integer id);

    Bid updateBid(Integer id, Bid bid);

    boolean deleteBid(Integer id);
}
