package com.openclassrooms.poseidon.service.serviceImpl;

import com.openclassrooms.poseidon.entity.RuleName;
import com.openclassrooms.poseidon.exception.RuleNameNotFoundException;
import com.openclassrooms.poseidon.repository.RuleNameRepository;
import com.openclassrooms.poseidon.service.RuleNameService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RuleNameServiceImpl implements RuleNameService {

    private final RuleNameRepository ruleNameRepository;

    private static final Logger log = LoggerFactory.getLogger(RuleNameServiceImpl.class);

    public RuleNameServiceImpl(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }




    @Override
    public List<RuleName> getAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    @Transactional
    @Override
    public RuleName createRuleName(RuleName ruleName) {
        log.info("Création d'un nouveau ruleName : {}", ruleName);
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName getRuleNameById(Integer id) {
        log.info("Récupération de l'id : {}", id);
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> new RuleNameNotFoundException("La règle n'a pas été trouvée : " + id));
    }

    @Transactional
    @Override
    public RuleName updateRuleName(Integer id, RuleName ruleName) {

        RuleName existingRuleName = getRuleNameById(id);
        existingRuleName.setName(ruleName.getName());
        existingRuleName.setDescription(ruleName.getDescription());
        existingRuleName.setJson(ruleName.getJson());
        existingRuleName.setTemplate(ruleName.getTemplate());
        existingRuleName.setSqlStr(ruleName.getSqlStr());
        existingRuleName.setSqlPart(ruleName.getSqlPart());

        log.info("Mise à jour de la règle : id= {}, ruleName= [{}]", id, existingRuleName);
        return ruleNameRepository.save(existingRuleName);
    }

    @Transactional
    @Override
    public boolean deleteRuleName(Integer id) {
        RuleName ruleName = getRuleNameById(id);
        ruleNameRepository.delete(ruleName);
        log.info("Suppression de la règle : {}", id);
        return true;
    }



}
