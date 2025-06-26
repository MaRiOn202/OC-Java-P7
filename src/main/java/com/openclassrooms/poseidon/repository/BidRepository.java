package com.openclassrooms.poseidon.repository;

import com.openclassrooms.poseidon.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BidRepository extends JpaRepository<Bid, Integer> {

}
