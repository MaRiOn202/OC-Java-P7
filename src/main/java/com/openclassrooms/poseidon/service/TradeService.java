package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.Trade;

import java.util.List;


public interface TradeService {


    List<Trade> getAllTrades();


    Trade createTrade(Trade trade);


    Trade getTradeById(Integer id);


    Trade updateTrade(Integer id, Trade trade);


    boolean deleteTrade(Integer id);

}
