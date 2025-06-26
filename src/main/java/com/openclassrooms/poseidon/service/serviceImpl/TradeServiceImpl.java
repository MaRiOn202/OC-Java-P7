package com.openclassrooms.poseidon.service.serviceImpl;

import com.openclassrooms.poseidon.entity.Trade;
import com.openclassrooms.poseidon.exception.TradeNotFoundException;
import com.openclassrooms.poseidon.repository.TradeRepository;
import com.openclassrooms.poseidon.service.TradeService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

      private final TradeRepository tradeRepository;


      private static final Logger log = LoggerFactory.getLogger(TradeServiceImpl.class);


    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }




    @Override
    public List<Trade> getAllTrades() {

        return tradeRepository.findAll();
    }

    @Transactional
    @Override
    public Trade createTrade(Trade trade) {
        log.info("Création d'un nouveau trade : {}", trade);
        return tradeRepository.save(trade);
    }

    @Override
    public Trade getTradeById(Integer id) {
        log.info("Récupération de l'id : {}", id);
        return tradeRepository.findById(id)
                .orElseThrow(() -> new TradeNotFoundException("Le trade n'a pas été trouvé : " + id));
    }

    @Transactional
    @Override
    public Trade updateTrade(Integer id, Trade trade) {

        Trade existingTrade = getTradeById(id);

        existingTrade.setAccount(trade.getAccount());
        existingTrade.setType(trade.getType());
        existingTrade.setBuyQuantity(trade.getBuyQuantity());

        log.info("Mise à jour du trade : id={}, avec les nouvelles données trade=[{}]", id, existingTrade);
        return tradeRepository.save(existingTrade);
    }

    @Transactional
    @Override
    public boolean deleteTrade(Integer id) {
        Trade trade = getTradeById(id);
        tradeRepository.delete(trade);
        log.info("Suppression du trade : {}", id);
        return true;
    }


}
