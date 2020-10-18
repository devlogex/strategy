package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.FilterInfoRepresentation;
import com.tnd.pw.strategy.common.representations.InitiativeRepresentation;
import com.tnd.pw.strategy.common.representations.ListInitiativeRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.exception.InvalidDataRequestException;
import com.tnd.pw.strategy.runner.service.InitiativeServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@HandlerServiceClass
public class InitiativeHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitiativeHandler.class);
    @Autowired
    private InitiativeServiceHandler initiativeServiceHandler;

    @HandlerService(path = "/strategy/initiative/add", protocol = "POST")
    public BaseResponse<ListInitiativeRepresentation> addInitiative(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException {
        LOGGER.info("[InitiativeHandler] addInitiative() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.addInitiative(request);
        LOGGER.info("[InitiativeHandler] addInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/update", protocol = "POST")
    public BaseResponse<InitiativeRepresentation> updateInitiative(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, LayoutNotFoundException, ActionServiceFailedException {
        LOGGER.info("[InitiativeHandler] updateInitiative() - request: {}", GsonUtils.convertToString(request));
        InitiativeRepresentation response = initiativeServiceHandler.updateInitiative(request);
        LOGGER.info("[InitiativeHandler] updateInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/update/status", protocol = "POST")
    public BaseResponse<ListInitiativeRepresentation> updateInitiativeState(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, LayoutNotFoundException, InvalidDataRequestException {
        LOGGER.info("[InitiativeHandler] updateInitiativeState() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.updateInitiativeState(request);
        LOGGER.info("[InitiativeHandler] updateInitiativeState() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative", protocol = "GET")
    public BaseResponse<ListInitiativeRepresentation> getInitiative(StrategyRequest request) throws DBServiceException, LayoutNotFoundException {
        LOGGER.info("[InitiativeHandler] getInitiative() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.getInitiative(request);
        LOGGER.info("[InitiativeHandler] getInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/info", protocol = "GET")
    public BaseResponse<InitiativeRepresentation> getInitiativeInfo(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, InitiativeNotFoundException, ActionServiceFailedException {
        LOGGER.info("[InitiativeHandler] getInitiativeInfo() - request: {}", GsonUtils.convertToString(request));
        InitiativeRepresentation response = initiativeServiceHandler.getInitiativeInfo(request);
        LOGGER.info("[InitiativeHandler] getInitiativeInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/status", protocol = "GET")
    public BaseResponse<ListInitiativeRepresentation> getInitiativeState(StrategyRequest request) throws DBServiceException, LayoutNotFoundException {
        LOGGER.info("[InitiativeHandler] getInitiativeState() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.getInitiativeState(request);
        LOGGER.info("[InitiativeHandler] getInitiativeState() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/delete", protocol = "POST")
    public BaseResponse<ListInitiativeRepresentation> removeInitiative(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, InitiativeNotFoundException {
        LOGGER.info("[InitiativeHandler] removeInitiative() - request: {}", GsonUtils.convertToString(request));
        ListInitiativeRepresentation response = initiativeServiceHandler.removeInitiative(request);
        LOGGER.info("[InitiativeHandler] removeInitiative() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/initiative/filter_info", protocol = "GET")
    public BaseResponse<FilterInfoRepresentation> getFilterInfos(StrategyRequest request) throws DBServiceException {
        LOGGER.info("[InitiativeHandler] getFilterInfos() - request: {}", GsonUtils.convertToString(request));
        FilterInfoRepresentation response = initiativeServiceHandler.getFilterInfos(request);
        LOGGER.info("[InitiativeHandler] getFilterInfos() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
