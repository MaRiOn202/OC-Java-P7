package com.openclassrooms.poseidon.service;

import com.openclassrooms.poseidon.domain.BidList;

import java.util.List;

public interface BidListService {


    List<BidList> getAllBids();

    BidList createBid(BidList bidList);

    BidList getBidById(Integer id);

    BidList updateBid(Integer id, BidList bidList);

    boolean deleteBid(Integer id);
}
