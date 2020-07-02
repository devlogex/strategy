package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.CompetitorRepresentation;
import com.tnd.pw.strategy.common.representations.ListCompetitorRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.competitor.exception.CompetitorNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;

import java.io.IOException;

public interface CompetitorServiceHandler extends ServiceHandler{
    ListCompetitorRepresentation addCompetitor(StrategyRequest request) throws IOException, DBServiceException, CompetitorNotFoundException;
    CompetitorRepresentation updateCompetitor(StrategyRequest request) throws DBServiceException, IOException, CompetitorNotFoundException;
    ListCompetitorRepresentation getCompetitor(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException;
    ListCompetitorRepresentation removeCompetitor(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, CompetitorNotFoundException;
}
