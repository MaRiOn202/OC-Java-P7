package com.openclassrooms.poseidon.service;


import com.openclassrooms.poseidon.entity.RuleName;

import java.util.List;


public interface RuleNameService {

    List<RuleName> getAllRuleNames();


    RuleName createRuleName(RuleName ruleName);


    RuleName getRuleNameById(Integer id);


    RuleName updateRuleName(Integer id, RuleName ruleName);


    boolean deleteRuleName(Integer id);
















}
