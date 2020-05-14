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
import com.tnd.pw.strategy.common.requests.VisionRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.runner.service.VisionHandlerBuz;
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
    private VisionHandlerBuz visionHandlerBuz;

    @HandlerService(path = "/strategy/vision/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionRepresentation> addVision(BaseRequest<VisionRequest> request) throws IOException, DBServiceException {
        LOGGER.info("[VisionHandler] addVision() - request: {}", GsonUtils.convertToString(request));
        VisionRepresentation response = visionHandlerBuz.addVision(request.getData());
        LOGGER.info("[VisionHandler] addVision() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionRepresentation> updateVision(BaseRequest<VisionRequest> request) throws DBServiceException, VisionNotFoundException, IOException {
        LOGGER.info("[VisionHandler] updateVision() - request: {}", GsonUtils.convertToString(request));
        VisionRepresentation response = visionHandlerBuz.updateVision(request.getData());
        LOGGER.info("[VisionHandler] updateVision() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionRepresentation> getVision(BaseRequest<VisionRequest> request) throws DBServiceException, IOException {
        LOGGER.info("[VisionHandler] getVision() - request: {}", GsonUtils.convertToString(request));
        VisionRepresentation response = visionHandlerBuz.getVision(request.getData());
        LOGGER.info("[VisionHandler] getVision() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionComponentRep> addVisionComponent(BaseRequest<VisionRequest> request) throws IOException, DBServiceException {
        LOGGER.info("[VisionHandler] addVisionComponent() - request: {}", GsonUtils.convertToString(request));
        VisionComponentRep response = visionHandlerBuz.addVisionComponent(request.getData());
        LOGGER.info("[VisionHandler] addVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionComponentRep> updateVisionComponent(BaseRequest<VisionRequest> request) throws DBServiceException, IOException, VisionComponentNotFoundException {
        LOGGER.info("[VisionHandler] updateVisionComponent() - request: {}", GsonUtils.convertToString(request));
        VisionComponentRep response = visionHandlerBuz.updateVisionComponent(request.getData());
        LOGGER.info("[VisionHandler] updateVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<ListVisionComponentRep> getVisionComponent(BaseRequest<VisionRequest> request) throws DBServiceException, IOException {
        LOGGER.info("[VisionHandler] getVisionComponent() - request: {}", GsonUtils.convertToString(request));
        ListVisionComponentRep response = visionHandlerBuz.getVisionComponent(request.getData());
        LOGGER.info("[VisionHandler] getVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
