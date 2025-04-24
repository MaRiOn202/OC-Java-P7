package com.openclassrooms.poseidon.service.serviceImpl;

import com.openclassrooms.poseidon.entity.Trade;
import com.openclassrooms.poseidon.exception.TradeNotFoundException;
import com.openclassrooms.poseidon.repositories.TradeRepository;
import com.openclassrooms.poseidon.service.TradeService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
                .orElseThrow(() -> new TradeNotFoundException("Le trade n'a pas été trouvé" + id));
    }

    @Transactional
    @Override
    public Trade updateTrade(Integer id, Trade trade) {

        // en commentaire ce qu'on ne veut pas update
        Trade existingTrade = getTradeById(id);

        //existingTrade.setTradeId(trade.getTradeId());
        existingTrade.setAccount(trade.getAccount());
        existingTrade.setType(trade.getType());
        existingTrade.setBuyQuantity(trade.getBuyQuantity());
        existingTrade.setSellQuantity(trade.getSellQuantity());
        //existingTrade.setBuyPrice(trade.getBuyPrice());
        //existingTrade.setSellPrice(trade.getSellPrice());
        existingTrade.setBenchmark(trade.getBenchmark());
        existingTrade.setTradeDate(existingTrade.getTradeDate()); //current date
        existingTrade.setSecurity(trade.getSecurity());
        existingTrade.setStatus(trade.getStatus());
        existingTrade.setTrader(trade.getTrader());
        existingTrade.setBook(trade.getBook());
        existingTrade.setCreationName(trade.getCreationName());
        existingTrade.setCreationDate(existingTrade.getCreationDate()); // current date
        existingTrade.setRevisionName(trade.getRevisionName());
        existingTrade.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        existingTrade.setDealName(trade.getDealName());
        existingTrade.setDealType(trade.getDealType());
        existingTrade.setSourceListId(trade.getSourceListId());
        existingTrade.setSide(trade.getSide());

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
