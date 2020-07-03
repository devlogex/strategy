package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.InitiativeRepresentation;
import com.tnd.pw.strategy.common.representations.ListInitiativeRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.exception.InvalidDataRequestException;
import com.tnd.pw.strategy.runner.service.InitiativeServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class InitiativeHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitiativeHandler.class);
    @Autowired
    private InitiativeServiceHandler initiativeServiceHandler;

    @HandlerService(path = "/strategy/initiative/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListInitiativeRepresentation> addInitiative(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, InitiativeNotFoundException {
        LOGGER.info("[InitiativeHandler] addInitiative() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.addInitiative(request.getData());
        LOGGER.info("[InitiativeHandler] addInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<InitiativeRepresentation> updateInitiative(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, InitiativeNotFoundException {
        LOGGER.info("[InitiativeHandler] updateInitiative() - request: {}", GsonUtils.convertToString(request));
        InitiativeRepresentation response = initiativeServiceHandler.updateInitiative(request.getData());
        LOGGER.info("[InitiativeHandler] updateInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/update/status", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListInitiativeRepresentation> updateInitiativeState(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, InitiativeNotFoundException, LayoutNotFoundException, InvalidDataRequestException {
        LOGGER.info("[InitiativeHandler] updateInitiativeState() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.updateInitiativeState(request.getData());
        LOGGER.info("[InitiativeHandler] updateInitiativeState() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListInitiativeRepresentation> getInitiative(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[InitiativeHandler] getInitiative() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.getInitiative(request.getData());
        LOGGER.info("[InitiativeHandler] getInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/status", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListInitiativeRepresentation> getInitiativeState(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[InitiativeHandler] getInitiativeState() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.getInitiativeState(request.getData());
        LOGGER.info("[InitiativeHandler] getInitiativeState() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListInitiativeRepresentation> removeInitiative(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, InitiativeNotFoundException {
        LOGGER.info("[InitiativeHandler] removeInitiative() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.removeInitiative(request.getData());
        LOGGER.info("[InitiativeHandler] removeInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
