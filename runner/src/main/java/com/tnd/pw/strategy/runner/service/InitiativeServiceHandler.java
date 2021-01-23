package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.CsStrategyRep;
import com.tnd.pw.strategy.common.representations.FilterInfoRepresentation;
import com.tnd.pw.strategy.common.representations.InitiativeRep;
import com.tnd.pw.strategy.common.representations.ListInitiativeRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.exception.DevServiceFailedException;
import com.tnd.pw.strategy.runner.exception.InvalidDataRequestException;

public interface InitiativeServiceHandler extends ServiceHandler {
    ListInitiativeRepresentation addInitiative(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException;
    InitiativeRep updateInitiative(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, LayoutNotFoundException, ActionServiceFailedException;
    ListInitiativeRepresentation getInitiative(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    InitiativeRep getInitiativeInfo(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, ActionServiceFailedException;
    ListInitiativeRepresentation removeInitiative(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, InitiativeNotFoundException;
    ListInitiativeRepresentation getInitiativeState(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    ListInitiativeRepresentation updateInitiativeState(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, LayoutNotFoundException, InvalidDataRequestException;

    FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws DBServiceException;

    CsStrategyRep getInitiativeRoadmap(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, DevServiceFailedException;
}
