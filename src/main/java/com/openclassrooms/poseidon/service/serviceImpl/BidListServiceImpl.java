package com.openclassrooms.poseidon.service.serviceImpl;

import com.openclassrooms.poseidon.domain.BidList;
import com.openclassrooms.poseidon.exception.BidListNotFound;
import com.openclassrooms.poseidon.repositories.BidListRepository;
import com.openclassrooms.poseidon.service.BidListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {


    private final BidListRepository bidListRepository;

    private final Logger log = LoggerFactory.getLogger(BidListServiceImpl.class);

    public BidListServiceImpl(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }




    @Override
    public List<BidList> getAllBids() {
        return bidListRepository.findAll();
    }

    @Override
    public BidList createBid(BidList bidList) {

        log.info("Création d'une nouvelle offre : {}", bidList);
        return bidListRepository.save(bidList);
    }

    @Override
    public BidList getBidById(Integer id) {
        log.info("Récupération de l'id : {}", id);
        return bidListRepository.findById(id)
                .orElseThrow(() -> new BidListNotFound("L'offre n'a pas été trouvée" + id));
    }

    @Override
    public BidList updateBid(Integer id, BidList bidList) {

        // Est-ce qu'on peut modifier tout ? A voir + tard
        BidList existingBid = getBidById(id);
        existingBid.setAccount(bidList.getAccount());
        existingBid.setType(bidList.getType());
        existingBid.setBidQuantity(bidList.getBidQuantity());
        existingBid.setAskQuantity(bidList.getAskQuantity());
        existingBid.setBid(bidList.getBid());
        existingBid.setAsk(bidList.getAsk());
        existingBid.setBenchmark(bidList.getBenchmark());
        existingBid.setBidListDate(bidList.getBidListDate());
        existingBid.setCommentary(bidList.getCommentary());
        existingBid.setSecurity(bidList.getSecurity());
        existingBid.setStatus(bidList.getStatus());
        existingBid.setTrader(bidList.getTrader());
        existingBid.setBook(bidList.getBook());
        existingBid.setCreationName(bidList.getCreationName());
        //existingBid.setCreationDate(bidList.getCreationDate());
        existingBid.setRevisionName(bidList.getRevisionName());
        existingBid.setRevisionDate(new Timestamp(System.currentTimeMillis()));
        existingBid.setDealName(bidList.getDealName());
        existingBid.setDealType(bidList.getDealType());
        existingBid.setSourceListId(bidList.getSourceListId());
        existingBid.setSide(bidList.getSide());

        log.info("Mise à jour de l'offre : {}", id);
        return bidListRepository.save(existingBid);
    }

    @Override
    public boolean deleteBid(Integer id) {
        BidList bidList = getBidById(id);
        bidListRepository.delete(bidList);
        log.info("Suppression de l'offre : {}", id);
        return true;
    }


}
