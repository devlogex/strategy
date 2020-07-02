package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.ListPositionRepresentation;
import com.tnd.pw.strategy.common.representations.PositionComponentRep;
import com.tnd.pw.strategy.common.representations.PositionRepresentation;
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

    @HandlerService(path = "/strategy/position/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListPositionRepresentation> addPosition(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, PositionNotFoundException {
        LOGGER.info("[PositionHandler] addPosition() - request: {}", GsonUtils.convertToString(request));
        ListPositionRepresentation response = positionServiceHandler.addPosition(request.getData());
        LOGGER.info("[PositionHandler] addPosition() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<PositionRepresentation> updatePosition(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, PositionNotFoundException {
        LOGGER.info("[PositionHandler] updatePosition() - request: {}", GsonUtils.convertToString(request));
        PositionRepresentation response = positionServiceHandler.updatePosition(request.getData());
        LOGGER.info("[PositionHandler] updatePosition() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListPositionRepresentation> getPosition(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[PositionHandler] getPosition() - request: {}", GsonUtils.convertToString(request));
        ListPositionRepresentation response = positionServiceHandler.getPosition(request.getData());
        LOGGER.info("[PositionHandler] getPosition() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListPositionRepresentation> removePosition(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, PositionNotFoundException {
        LOGGER.info("[PositionHandler] removePosition() - request: {}", GsonUtils.convertToString(request));
        ListPositionRepresentation response = positionServiceHandler.removePosition(request.getData());
        LOGGER.info("[PositionHandler] removePosition() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<LayoutRepresentation> addPositionComponent(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] addPositionComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = positionServiceHandler.addPositionComponent(request.getData());
        LOGGER.info("[PositionHandler] addPositionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<PositionComponentRep> updatePositionComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] updatePositionComponent() - request: {}", GsonUtils.convertToString(request));
        PositionComponentRep response = positionServiceHandler.updatePositionComponent(request.getData());
        LOGGER.info("[PositionHandler] updatePositionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<LayoutRepresentation> getPositionComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] getPositionComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = positionServiceHandler.getPositionComponent(request.getData());
        LOGGER.info("[PositionHandler] getPositionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/position_component/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<LayoutRepresentation> removePositionComponent(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, PositionComponentNotFoundException {
        LOGGER.info("[PositionHandler] removePositionComponent() - request: {}", GsonUtils.convertToString(request));
        LayoutRepresentation response = positionServiceHandler.removePositionComponent(request.getData());
        LOGGER.info("[PositionHandler] removePositionComponent() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}