package com.tnd.pw.strategy.runner.handler;

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

    @HandlerService(path = "/strategy/model/add", protocol = "POST")
    public BaseResponse<ListModelRepresentation> addModel(StrategyRequest request) throws IOException, DBServiceException, ModelNotFoundException {
        LOGGER.info("[ModelHandler] addModel() - request: {}", GsonUtils.convertToString(request));
        ListModelRepresentation response = modelServiceHandler.addModel(request);
        LOGGER.info("[ModelHandler] addModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model/update", protocol = "POST")
    public BaseResponse<ModelRepresentation> updateModel(StrategyRequest request) throws DBServiceException, IOException, ModelNotFoundException {
        LOGGER.info("[ModelHandler] updateModel() - request: {}", GsonUtils.convertToString(request));
        ModelRepresentation response = modelServiceHandler.updateModel(request);
        LOGGER.info("[ModelHandler] updateModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model", protocol = "GET")
    public BaseResponse<ListModelRepresentation> getModel(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[ModelHandler] getModel() - request: {}", GsonUtils.convertToString(request));
        ListModelRepresentation response = modelServiceHandler.getModel(request);
        LOGGER.info("[ModelHandler] getModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model/delete", protocol = "POST")
    public BaseResponse<ListModelRepresentation> removeModel(StrategyRequest request) throws DBServiceException, IOException, ModelNotFoundException, LayoutNotFoundException {
        LOGGER.info("[ModelHandler] removeModel() - request: {}", GsonUtils.convertToString(request));
        ListModelRepresentation response = modelServiceHandler.removeModel(request);
        LOGGER.info("[ModelHandler] removeModel() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component/add", protocol = "POST")
    public BaseResponse<LayoutRepresentation> addModelComponent(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] addModelComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = modelServiceHandler.addModelComponent(request);
        LOGGER.info("[ModelHandler] addModelComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component/update", protocol = "POST")
    public BaseResponse<ModelComponentRep> updateModelComponent(StrategyRequest request) throws DBServiceException, IOException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] updateModelComponent() - request: {}", GsonUtils.convertToString(request));
        ModelComponentRep response = modelServiceHandler.updateModelComponent(request);
        LOGGER.info("[ModelHandler] updateModelComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component/id", protocol = "GET")
    public BaseResponse<LayoutRepresentation> getModelComponentById(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] getModelComponentById() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = modelServiceHandler.getModelComponentById(request);
        LOGGER.info("[ModelHandler] getModelComponentById() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component/model_id", protocol = "GET")
    public BaseResponse<LayoutRepresentation> getModelComponentByModelId(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] getModelComponentByModelId() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = modelServiceHandler.getModelComponentByModelId(request);
        LOGGER.info("[ModelHandler] getModelComponentByModelId() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model_component/delete", protocol = "POST")
    public BaseResponse<LayoutRepresentation> removeModelComponent(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, ModelComponentNotFoundException {
        LOGGER.info("[ModelHandler] removeVisionComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = modelServiceHandler.removeModelComponent(request);
        LOGGER.info("[ModelHandler] removeVisionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/model/filter_info", protocol = "GET")
    public BaseResponse<FilterInfoRepresentation> getTimeFrames(StrategyRequest request) throws IOException, DBServiceException {
        LOGGER.info("[ModelHandler] getTimeFrames() - request: {}", GsonUtils.convertToString(request));
        FilterInfoRepresentation response = modelServiceHandler.getFilterInfos(request);
        LOGGER.info("[ModelHandler] getTimeFrames() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}