package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.InitiativeRepresentation;
import com.tnd.pw.strategy.common.representations.ListInitiativeRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;

import java.io.IOException;

public interface InitiativeServiceHandler extends ServiceHandler {
    ListInitiativeRepresentation addInitiative(StrategyRequest request) throws IOException, DBServiceException, InitiativeNotFoundException;
    InitiativeRepresentation updateInitiative(StrategyRequest request) throws DBServiceException, IOException, InitiativeNotFoundException;
    ListInitiativeRepresentation getInitiative(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException;
    ListInitiativeRepresentation removeInitiative(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, InitiativeNotFoundException;
}
