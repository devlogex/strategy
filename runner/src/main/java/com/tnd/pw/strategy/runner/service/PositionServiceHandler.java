package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;

import java.io.IOException;

public interface PositionServiceHandler extends ServiceHandler {
    ListPositionRepresentation addPosition(StrategyRequest request) throws DBServiceException, PositionNotFoundException;
    PositionRepresentation updatePosition(StrategyRequest request) throws DBServiceException, PositionNotFoundException, ActionServiceFailedException;
    ListPositionRepresentation getPosition(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    PositionRepresentation getPositionInfo(StrategyRequest request) throws DBServiceException, PositionNotFoundException, ActionServiceFailedException;
    ListPositionRepresentation removePosition(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, PositionNotFoundException;

    LayoutRepresentation addPositionComponent(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException;
    PositionComponentRep updatePositionComponent(StrategyRequest request) throws DBServiceException, PositionComponentNotFoundException;
    LayoutRepresentation getPositionComponentById(StrategyRequest request) throws DBServiceException;
    LayoutRepresentation getPositionComponentByPositionId(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    LayoutRepresentation removePositionComponent(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException;

    FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws DBServiceException;
}