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
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;
import com.tnd.pw.strategy.runner.service.PositionServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class PositionHandler  implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionHandler.class);
    @Autowired
    private PositionServiceHandler positionServiceHandler;

    @HandlerService(path = "/strategy/position/add", protocol = "POST")
    public BaseResponse<ListPositionRepresentation> addPosition(StrategyRequest request) throws IOException, DBServiceException, PositionNotFoundException {
        LOGGER.info("[PositionHandler] addPosition() - request: {}", GsonUtils.convertToString(request));
        ListPositionRepresentation response = positionServiceHandler.addPosition(request);
        LOGGER.info("[PositionHandler] addPosition() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position/update", protocol = "POST")
    public BaseResponse<PositionRepresentation> updatePosition(StrategyRequest request) throws DBServiceException, IOException, PositionNotFoundException {
        LOGGER.info("[PositionHandler] updatePosition() - request: {}", GsonUtils.convertToString(request));
        PositionRepresentation response = positionServiceHandler.updatePosition(request);
        LOGGER.info("[PositionHandler] updatePosition() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position", protocol = "GET")
    public BaseResponse<ListPositionRepresentation> getPosition(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[PositionHandler] getPosition() - request: {}", GsonUtils.convertToString(request));
        ListPositionRepresentation response = positionServiceHandler.getPosition(request);
        LOGGER.info("[PositionHandler] getPosition() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position/delete", protocol = "POST")
    public BaseResponse<ListPositionRepresentation> removePosition(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, PositionNotFoundException {
        LOGGER.info("[PositionHandler] removePosition() - request: {}", GsonUtils.convertToString(request));
        ListPositionRepresentation response = positionServiceHandler.removePosition(request);
        LOGGER.info("[PositionHandler] removePosition() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component/add", protocol = "POST")
    public BaseResponse<LayoutRepresentation> addPositionComponent(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] addPositionComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = positionServiceHandler.addPositionComponent(request);
        LOGGER.info("[PositionHandler] addPositionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component/update", protocol = "POST")
    public BaseResponse<PositionComponentRep> updatePositionComponent(StrategyRequest request) throws DBServiceException, IOException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] updatePositionComponent() - request: {}", GsonUtils.convertToString(request));
        PositionComponentRep response = positionServiceHandler.updatePositionComponent(request);
        LOGGER.info("[PositionHandler] updatePositionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component/id", protocol = "GET")
    public BaseResponse<LayoutRepresentation> getPositionComponentById(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] getPositionComponentById() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = positionServiceHandler.getPositionComponentById(request);
        LOGGER.info("[PositionHandler] getPositionComponentById() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component/position_id", protocol = "GET")
    public BaseResponse<LayoutRepresentation> getPositionComponentByPositionId(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] getPositionComponentByPositionId() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = positionServiceHandler.getPositionComponentByPositionId(request);
        LOGGER.info("[PositionHandler] getPositionComponentByPositionId() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component/delete", protocol = "POST")
    public BaseResponse<LayoutRepresentation> removePositionComponent(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] removePositionComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = positionServiceHandler.removePositionComponent(request);
        LOGGER.info("[PositionHandler] removePositionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position/filter_info", protocol = "GET")
    public BaseResponse<FilterInfoRepresentation> getFilterInfos(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[PositionHandler] getFilterInfos() - request: {}", GsonUtils.convertToString(request));
        FilterInfoRepresentation response = positionServiceHandler.getFilterInfos(request);
        LOGGER.info("[PositionHandler] getFilterInfos() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}