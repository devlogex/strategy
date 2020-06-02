package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;
import com.tnd.pw.strategy.runner.service.ModelServiceHandler;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class ModelHandler  implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelHandler.class);
    @Autowired
    private ModelServiceHandler modelServiceHandler;

    @HandlerService(path = "/strategy/model/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListModelRepresentation> addModel(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, ModelNotFoundException {
        LOGGER.info("[ModelHandler] addModel() - request: {}", GsonUtils.convertToString(request));
        ListModelRepresentation response = modelServiceHandler.addModel(request.getData());
        LOGGER.info("[ModelHandler] addModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ModelRepresentation> updateModel(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, ModelNotFoundException {
        LOGGER.info("[ModelHandler] updateModel() - request: {}", GsonUtils.convertToString(request));
        ModelRepresentation response = modelServiceHandler.updateModel(request.getData());
        LOGGER.info("[ModelHandler] updateModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListModelRepresentation> getModel(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[ModelHandler] getModel() - request: {}", GsonUtils.convertToString(request));
        ListModelRepresentation response = modelServiceHandler.getModel(request.getData());
        LOGGER.info("[ModelHandler] getModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListModelRepresentation> removeModel(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, VisionComponentNotFoundException, ModelNotFoundException, LayoutNotFoundException {
        LOGGER.info("[ModelHandler] removeModel() - request: {}", GsonUtils.convertToString(request));
        ListModelRepresentation response = modelServiceHandler.removeModel(request.getData());
        LOGGER.info("[ModelHandler] removeModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

//    @HandlerService(path = "/strategy/vision_component/add", protocol = "POST",
//            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
//    public BaseResponse<ListVisionComponentRep> addVisionComponent(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, VisionNotFoundException, LayoutNotFoundException, VisionComponentNotFoundException {
//        LOGGER.info("[VisionHandler] addVisionComponent() - request: {}", GsonUtils.convertToString(request));
//        ListVisionComponentRep response = modelServiceHandler.addVisionComponent(request.getData());
//        LOGGER.info("[VisionHandler] addVisionComponent() - response: {}", GsonUtils.convertToString(response));
//        return new BaseResponse<>(response);
//    }
//
//    @HandlerService(path = "/strategy/vision_component/update", protocol = "POST",
//            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
//    public BaseResponse<VisionComponentRep> updateVisionComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, VisionComponentNotFoundException {
//        LOGGER.info("[VisionHandler] updateVisionComponent() - request: {}", GsonUtils.convertToString(request));
//        VisionComponentRep response = modelServiceHandler.updateVisionComponent(request.getData());
//        LOGGER.info("[VisionHandler] updateVisionComponent() - response: {}", GsonUtils.convertToString(response));
//        return new BaseResponse<>(response);
//    }
//
//    @HandlerService(path = "/strategy/vision_component", protocol = "GET",
//            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
//    public BaseResponse<ListVisionComponentRep> getVisionComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException {
//        LOGGER.info("[VisionHandler] getVisionComponent() - request: {}", GsonUtils.convertToString(request));
//        ListVisionComponentRep response = modelServiceHandler.getVisionComponent(request.getData());
//        LOGGER.info("[VisionHandler] getVisionComponent() - response: {}", GsonUtils.convertToString(response));
//        return new BaseResponse<>(response);
//    }
//
//    @HandlerService(path = "/strategy/vision_component/delete", protocol = "POST",
//            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
//    public BaseResponse<ListVisionComponentRep> removeVisionComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, VisionComponentNotFoundException {
//        LOGGER.info("[VisionHandler] removeVisionComponent() - request: {}", GsonUtils.convertToString(request));
//        ListVisionComponentRep response = modelServiceHandler.removeVisionComponent(request.getData());
//        LOGGER.info("[VisionHandler] removeVisionComponent() - response: {}", GsonUtils.convertToString(response));
//        return new BaseResponse<>(response);
//    }
}