package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;
import com.tnd.pw.strategy.common.representations.ListVisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;

import java.io.IOException;

public interface VisionServiceHandler extends ServiceHandler{
    VisionRepresentation addVision(StrategyRequest request) throws IOException, DBServiceException, CallApiFailException;
    VisionRepresentation updateVision(StrategyRequest request) throws DBServiceException, VisionNotFoundException, IOException, CallApiFailException;
    VisionRepresentation getVision(StrategyRequest request) throws DBServiceException, IOException, CallApiFailException;

    ListVisionComponentRep addVisionComponent(StrategyRequest request) throws IOException, DBServiceException, VisionNotFoundException, LayoutNotFoundException, VisionComponentNotFoundException;
    VisionComponentRep updateVisionComponent(StrategyRequest request) throws DBServiceException, IOException, VisionComponentNotFoundException;
    ListVisionComponentRep getVisionComponentById(StrategyRequest request) throws IOException, DBServiceException;
    ListVisionComponentRep getVisionComponentByVisionId(StrategyRequest request) throws DBServiceException, IOException;
    ListVisionComponentRep removeVisionComponent(StrategyRequest data) throws IOException, DBServiceException, VisionComponentNotFoundException, LayoutNotFoundException;
}
