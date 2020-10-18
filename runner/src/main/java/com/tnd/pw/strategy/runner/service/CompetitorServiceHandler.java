package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.CompetitorRepresentation;
import com.tnd.pw.strategy.common.representations.ListCompetitorRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.competitor.exception.CompetitorNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;


public interface CompetitorServiceHandler extends ServiceHandler{
    ListCompetitorRepresentation addCompetitor(StrategyRequest request) throws DBServiceException, CompetitorNotFoundException;
    CompetitorRepresentation updateCompetitor(StrategyRequest request) throws DBServiceException, CompetitorNotFoundException, ActionServiceFailedException;
    ListCompetitorRepresentation getCompetitor(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    CompetitorRepresentation getCompetitorInfo(StrategyRequest request) throws DBServiceException, CompetitorNotFoundException, ActionServiceFailedException;
    ListCompetitorRepresentation removeCompetitor(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, CompetitorNotFoundException;
}
