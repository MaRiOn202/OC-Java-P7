package com.openclassrooms.poseidon.service.serviceImpl;

import com.openclassrooms.poseidon.entity.Bid;
import com.openclassrooms.poseidon.exception.BidNotFoundException;
import com.openclassrooms.poseidon.repository.BidRepository;
import com.openclassrooms.poseidon.service.BidService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

        Bid existingBid = getBidById(id);

        existingBid.setAccount(bid.getAccount());
        existingBid.setType(bid.getType());
        existingBid.setBidQuantity(bid.getBidQuantity());

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
