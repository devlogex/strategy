package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.LayoutResponse;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.service.impl.LayoutServiceHandlerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class LayoutHandler implements BaseHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(LayoutHandler.class);
    @Autowired
    private LayoutServiceHandlerImpl layoutServiceHandlerImpl;

    @HandlerService(path = "/strategy/layout/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<LayoutResponse> updateLayout(BaseRequest<StrategyRequest> request) throws Exception {
        LOGGER.info("[LayoutHandler] updateLayout() - request: {}", GsonUtils.convertToString(request));
        LayoutResponse response = layoutServiceHandlerImpl.updateLayout(request.getData());
        LOGGER.info("[LayoutHandler] updateLayout() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
