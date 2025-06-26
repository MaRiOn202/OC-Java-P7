package com.openclassrooms.poseidon.repository;

import com.openclassrooms.poseidon.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<Trade, Integer> {


}
