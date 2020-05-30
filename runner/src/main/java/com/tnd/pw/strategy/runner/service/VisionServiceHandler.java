package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.ListVisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface VisionServiceHandler extends ServiceHandler{
    VisionRepresentation addVision(StrategyRequest request) throws IOException, DBServiceException;
    VisionRepresentation updateVision(StrategyRequest request) throws DBServiceException, VisionNotFoundException, IOException;
    VisionRepresentation getVision(StrategyRequest request) throws DBServiceException, IOException;

    ListVisionComponentRep addVisionComponent(StrategyRequest request) throws IOException, DBServiceException, VisionNotFoundException, LayoutNotFoundException, VisionComponentNotFoundException;
    VisionComponentRep updateVisionComponent(StrategyRequest request) throws DBServiceException, IOException, VisionComponentNotFoundException;
    ListVisionComponentRep getVisionComponent(StrategyRequest request) throws DBServiceException, IOException;
    ListVisionComponentRep removeVisionComponent(StrategyRequest data) throws IOException, DBServiceException, VisionComponentNotFoundException;
}
