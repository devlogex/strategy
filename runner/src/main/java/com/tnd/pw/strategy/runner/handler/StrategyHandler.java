package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListPositionRepresentation;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;

@HandlerServiceClass
public class StrategyHandler implements BaseHandler {

    @HandlerService(path = "/", protocol = "GET")
    public BaseResponse<ListPositionRepresentation> check(BaseRequest request) throws DBServiceException, PositionNotFoundException {
        return new BaseResponse<>(null);
    }
}
