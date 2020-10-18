package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListVisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;

import java.io.IOException;

public interface VisionServiceHandler extends ServiceHandler{
    VisionRepresentation addVision(StrategyRequest request) throws DBServiceException;
    VisionRepresentation updateVision(StrategyRequest request) throws DBServiceException, VisionNotFoundException, ActionServiceFailedException;
    VisionRepresentation getVision(StrategyRequest request) throws DBServiceException, ActionServiceFailedException;

    ListVisionComponentRep addVisionComponent(StrategyRequest request) throws DBServiceException, VisionNotFoundException, LayoutNotFoundException, VisionComponentNotFoundException;
    VisionComponentRep updateVisionComponent(StrategyRequest request) throws DBServiceException, VisionComponentNotFoundException;
    ListVisionComponentRep getVisionComponentById(StrategyRequest request) throws DBServiceException;
    ListVisionComponentRep getVisionComponentByVisionId(StrategyRequest request) throws DBServiceException;
    ListVisionComponentRep removeVisionComponent(StrategyRequest data) throws DBServiceException, VisionComponentNotFoundException, LayoutNotFoundException;
}
