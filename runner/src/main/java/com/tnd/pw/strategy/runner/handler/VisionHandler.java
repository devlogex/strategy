package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListVisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.service.VisionServiceHandler;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class VisionHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisionHandler.class);
    @Autowired
    private VisionServiceHandler visionServiceHandler;

    @HandlerService(path = "/strategy/vision/add", protocol = "POST")
    public BaseResponse<VisionRepresentation> addVision(StrategyRequest request) throws IOException, DBServiceException {
        LOGGER.info("[VisionHandler] addVision() - request: {}", GsonUtils.convertToString(request));
        VisionRepresentation response = visionServiceHandler.addVision(request);
        LOGGER.info("[VisionHandler] addVision() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision/update", protocol = "POST")
    public BaseResponse<VisionRepresentation> updateVision(StrategyRequest request) throws DBServiceException, VisionNotFoundException, IOException {
        LOGGER.info("[VisionHandler] updateVision() - request: {}", GsonUtils.convertToString(request));
        VisionRepresentation response = visionServiceHandler.updateVision(request);
        LOGGER.info("[VisionHandler] updateVision() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision", protocol = "GET")
    public BaseResponse<VisionRepresentation> getVision(StrategyRequest request) throws DBServiceException, IOException {
        LOGGER.info("[VisionHandler] getVision() - request: {}", GsonUtils.convertToString(request));
        VisionRepresentation response = visionServiceHandler.getVision(request);
        LOGGER.info("[VisionHandler] getVision() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/add", protocol = "POST")
    public BaseResponse<ListVisionComponentRep> addVisionComponent(StrategyRequest request) throws IOException, DBServiceException, VisionNotFoundException, LayoutNotFoundException, VisionComponentNotFoundException {
        LOGGER.info("[VisionHandler] addVisionComponent() - request: {}", GsonUtils.convertToString(request));
        ListVisionComponentRep response = visionServiceHandler.addVisionComponent(request);
        LOGGER.info("[VisionHandler] addVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/update", protocol = "POST")
    public BaseResponse<VisionComponentRep> updateVisionComponent(StrategyRequest request) throws DBServiceException, IOException, VisionComponentNotFoundException {
        LOGGER.info("[VisionHandler] updateVisionComponent() - request: {}", GsonUtils.convertToString(request));
        VisionComponentRep response = visionServiceHandler.updateVisionComponent(request);
        LOGGER.info("[VisionHandler] updateVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/id", protocol = "GET")
    public BaseResponse<ListVisionComponentRep> getVisionComponentById(StrategyRequest request) throws DBServiceException, IOException {
        LOGGER.info("[VisionHandler] getVisionComponentById() - request: {}", GsonUtils.convertToString(request));
        ListVisionComponentRep response = visionServiceHandler.getVisionComponentById(request);
        LOGGER.info("[VisionHandler] getVisionComponentById() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/vision_id", protocol = "GET")
    public BaseResponse<ListVisionComponentRep> getVisionComponentByVisionId(StrategyRequest request) throws DBServiceException, IOException {
        LOGGER.info("[VisionHandler] getVisionComponentByVisionId() - request: {}", GsonUtils.convertToString(request));
        ListVisionComponentRep response = visionServiceHandler.getVisionComponentByVisionId(request);
        LOGGER.info("[VisionHandler] getVisionComponentByVisionId() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/delete", protocol = "POST")
    public BaseResponse<ListVisionComponentRep> removeVisionComponent(StrategyRequest request) throws DBServiceException, IOException, VisionComponentNotFoundException, LayoutNotFoundException {
        LOGGER.info("[VisionHandler] removeVisionComponent() - request: {}", GsonUtils.convertToString(request));
        ListVisionComponentRep response = visionServiceHandler.removeVisionComponent(request);
        LOGGER.info("[VisionHandler] removeVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
