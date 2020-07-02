package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;

import java.io.IOException;

public interface PositionServiceHandler extends ServiceHandler {
    ListPositionRepresentation addPosition(StrategyRequest request) throws IOException, DBServiceException, PositionNotFoundException;

    PositionRepresentation updatePosition(StrategyRequest request) throws DBServiceException, IOException, PositionNotFoundException;

    ListPositionRepresentation getPosition(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException;

    ListPositionRepresentation removePosition(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionNotFoundException;

    LayoutRepresentation addPositionComponent(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException;

    PositionComponentRep updatePositionComponent(StrategyRequest request) throws DBServiceException, IOException, PositionComponentNotFoundException;

    LayoutRepresentation getPositionComponent(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, PositionComponentNotFoundException;

    LayoutRepresentation removePositionComponent(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException;
}