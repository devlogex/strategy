package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;

import java.io.IOException;

public interface ModelServiceHandler extends ServiceHandler{
    ListModelRepresentation addModel(StrategyRequest request) throws IOException, DBServiceException, ModelNotFoundException;
    ModelRepresentation updateModel(StrategyRequest request) throws DBServiceException, IOException, ModelNotFoundException;
    ListModelRepresentation getModel(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException;
    ListModelRepresentation removeModel(StrategyRequest request) throws IOException, DBServiceException, ModelNotFoundException, LayoutNotFoundException;

    LayoutRepresentation addModelComponent(StrategyRequest request) throws IOException, DBServiceException, ModelComponentNotFoundException, LayoutNotFoundException;
    ModelComponentRep updateModelComponent(StrategyRequest request) throws DBServiceException, IOException, ModelComponentNotFoundException;
    LayoutRepresentation getModelComponentById(StrategyRequest request) throws IOException, DBServiceException;
    LayoutRepresentation getModelComponentByModelId(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException;
    LayoutRepresentation removeModelComponent(StrategyRequest request) throws IOException, DBServiceException, ModelComponentNotFoundException, LayoutNotFoundException;

    FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws IOException, DBServiceException;
}