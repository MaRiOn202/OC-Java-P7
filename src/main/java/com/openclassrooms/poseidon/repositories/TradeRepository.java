package com.openclassrooms.poseidon.repositories;

import com.openclassrooms.poseidon.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TradeRepository extends JpaRepository<Trade, Integer> {


}
