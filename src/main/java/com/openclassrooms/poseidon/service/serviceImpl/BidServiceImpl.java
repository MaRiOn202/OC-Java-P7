package com.openclassrooms.poseidon.service.serviceImpl;

import com.openclassrooms.poseidon.entity.Bid;
import com.openclassrooms.poseidon.exception.BidNotFoundException;
import com.openclassrooms.poseidon.repositories.BidRepository;
import com.openclassrooms.poseidon.service.BidService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {


    private final BidRepository bidRepository;

    private static final Logger log = LoggerFactory.getLogger(BidServiceImpl.class);

    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }




    @Override
    public List<Bid> getAllBids() {

        return bidRepository.findAll();
    }

    @Transactional
    @Override
    public Bid createBid(Bid bid) {

        log.info("Création d'une nouvelle offre : {}", bid);
        return bidRepository.save(bid);
    }

    @Override
    public Bid getBidById(Integer id) {
        log.info("Récupération de l'id : {}", id);
        return bidRepository.findById(id)
                .orElseThrow(() -> new BidNotFoundException("L'offre n'a pas été trouvée : " + id));
    }

    @Transactional
    @Override
    public Bid updateBid(Integer id, Bid bid) {

        // Est-ce qu'on peut modifier tout ? A voir + tard
        Bid existingBid = getBidById(id);

        existingBid.setAccount(bid.getAccount());
        existingBid.setType(bid.getType());
        existingBid.setBidQuantity(bid.getBidQuantity());

        existingBid.setAskQuantity(bid.getAskQuantity());
        existingBid.setBid(bid.getBid());
        existingBid.setAsk(bid.getAsk());
        existingBid.setBenchmark(bid.getBenchmark());
        existingBid.setBidListDate(existingBid.getBidListDate());
        existingBid.setCommentary(bid.getCommentary());
        existingBid.setSecurity(bid.getSecurity());
        existingBid.setStatus(bid.getStatus());
        existingBid.setTrader(bid.getTrader());
        existingBid.setBook(bid.getBook());
        existingBid.setCreationName(bid.getCreationName());
        existingBid.setCreationDate(existingBid.getCreationDate());
        existingBid.setRevisionName(bid.getRevisionName());
        existingBid.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        existingBid.setDealName(bid.getDealName());
        existingBid.setDealType(bid.getDealType());
        existingBid.setSourceListId(bid.getSourceListId());
        existingBid.setSide(bid.getSide());

        log.info("Mise à jour de l'offre : id={} avec les nouvelles données bid=[{}]", id, existingBid);
        return bidRepository.save(existingBid);
    }

    @Transactional
    @Override
    public boolean deleteBid(Integer id) {
        Bid bid = getBidById(id);
        bidRepository.delete(bid);
        log.info("Suppression de l'offre : {}", id);
        return true;
    }


}
