package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;

import java.io.IOException;

public interface PositionServiceHandler extends ServiceHandler {
    ListPositionRepresentation addPosition(StrategyRequest request) throws IOException, DBServiceException, PositionNotFoundException;
    PositionRepresentation updatePosition(StrategyRequest request) throws DBServiceException, IOException, PositionNotFoundException, CallApiFailException;
    ListPositionRepresentation getPosition(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException;
    PositionRepresentation getPositionInfo(StrategyRequest request) throws DBServiceException, PositionNotFoundException, IOException, CallApiFailException;
    ListPositionRepresentation removePosition(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionNotFoundException;

    LayoutRepresentation addPositionComponent(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException;
    PositionComponentRep updatePositionComponent(StrategyRequest request) throws DBServiceException, IOException, PositionComponentNotFoundException;
    LayoutRepresentation getPositionComponentById(StrategyRequest request) throws IOException, DBServiceException;
    LayoutRepresentation getPositionComponentByPositionId(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, IOException;
    LayoutRepresentation removePositionComponent(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException;

    FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws IOException, DBServiceException;
}