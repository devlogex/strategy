package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;


public interface ModelServiceHandler extends ServiceHandler{
    ListModelRepresentation addModel(StrategyRequest request) throws DBServiceException, ModelNotFoundException;
    ModelRepresentation updateModel(StrategyRequest request) throws DBServiceException, ModelNotFoundException, ActionServiceFailedException;
    ListModelRepresentation getModel(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    ListModelRepresentation removeModel(StrategyRequest request) throws DBServiceException, ModelNotFoundException, LayoutNotFoundException;

    LayoutRepresentation addModelComponent(StrategyRequest request) throws DBServiceException, ModelComponentNotFoundException, LayoutNotFoundException;
    ModelComponentRep updateModelComponent(StrategyRequest request) throws DBServiceException, ModelComponentNotFoundException;
    LayoutRepresentation getModelComponentById(StrategyRequest request) throws DBServiceException;
    LayoutRepresentation getModelComponentByModelId(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    LayoutRepresentation removeModelComponent(StrategyRequest request) throws DBServiceException, ModelComponentNotFoundException, LayoutNotFoundException;

    FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws DBServiceException;

    ModelRepresentation getModelInfo(StrategyRequest request) throws DBServiceException, ModelNotFoundException, ActionServiceFailedException;
}