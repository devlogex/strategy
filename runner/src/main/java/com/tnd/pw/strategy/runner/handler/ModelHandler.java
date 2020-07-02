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
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;
import com.tnd.pw.strategy.runner.service.ModelServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class ModelHandler implements BaseHandler {
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

    @HandlerService(path = "/strategy/model/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListModelRepresentation> removeModel(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, ModelNotFoundException, LayoutNotFoundException {
        LOGGER.info("[ModelHandler] removeModel() - request: {}", GsonUtils.convertToString(request));
        ListModelRepresentation response = modelServiceHandler.removeModel(request.getData());
        LOGGER.info("[ModelHandler] removeModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<LayoutRepresentation> addModelComponent(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, LayoutNotFoundException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] addModelComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = modelServiceHandler.addModelComponent(request.getData());
        LOGGER.info("[ModelHandler] addModelComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ModelComponentRep> updateModelComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] updateModelComponent() - request: {}", GsonUtils.convertToString(request));
        ModelComponentRep response = modelServiceHandler.updateModelComponent(request.getData());
        LOGGER.info("[ModelHandler] updateModelComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<LayoutRepresentation> getModelComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] getVisionComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = modelServiceHandler.getModelComponent(request.getData());
        LOGGER.info("[ModelHandler] getVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<LayoutRepresentation> removeModelComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] removeVisionComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = modelServiceHandler.removeModelComponent(request.getData());
        LOGGER.info("[ModelHandler] removeVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}