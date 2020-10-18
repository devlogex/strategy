package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.FilterInfoRepresentation;
import com.tnd.pw.strategy.common.representations.InitiativeRepresentation;
import com.tnd.pw.strategy.common.representations.ListInitiativeRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.exception.InvalidDataRequestException;

import java.io.IOException;

public interface InitiativeServiceHandler extends ServiceHandler {
    ListInitiativeRepresentation addInitiative(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException;
    InitiativeRepresentation updateInitiative(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, LayoutNotFoundException, ActionServiceFailedException;
    ListInitiativeRepresentation getInitiative(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    InitiativeRepresentation getInitiativeInfo(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, ActionServiceFailedException;
    ListInitiativeRepresentation removeInitiative(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, InitiativeNotFoundException;
    ListInitiativeRepresentation getInitiativeState(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    ListInitiativeRepresentation updateInitiativeState(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, LayoutNotFoundException, InvalidDataRequestException;

    FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws DBServiceException;
}
