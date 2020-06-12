package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.CompetitorRepresentation;
import com.tnd.pw.strategy.common.representations.ListCompetitorRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.competitor.exception.CompetitorNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.service.CompetitorServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class CompetitorHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitorHandler.class);
    @Autowired
    private CompetitorServiceHandler competitorServiceHandler;

    @HandlerService(path = "/strategy/competitor/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListCompetitorRepresentation> addCompetitor(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, CompetitorNotFoundException {
        LOGGER.info("[CompetitorHandler] addCompetitor() - request: {}", GsonUtils.convertToString(request));
        ListCompetitorRepresentation response = competitorServiceHandler.addCompetitor(request.getData());
        LOGGER.info("[CompetitorHandler] addCompetitor() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/competitor/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<CompetitorRepresentation> updateCompetitor(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, CompetitorNotFoundException {
        LOGGER.info("[CompetitorHandler] updateCompetitor() - request: {}", GsonUtils.convertToString(request));
        CompetitorRepresentation response = competitorServiceHandler.updateCompetitor(request.getData());
        LOGGER.info("[CompetitorHandler] updateCompetitor() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/competitor", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListCompetitorRepresentation> getCompetitor(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[CompetitorHandler] getCompetitor() - request: {}", GsonUtils.convertToString(request));
        ListCompetitorRepresentation response = competitorServiceHandler.getCompetitor(request.getData());
        LOGGER.info("[CompetitorHandler] getCompetitor() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/competitor/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListCompetitorRepresentation> removeCompetitor(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, CompetitorNotFoundException {
        LOGGER.info("[CompetitorHandler] removeCompetitor() - request: {}", GsonUtils.convertToString(request));
        ListCompetitorRepresentation response = competitorServiceHandler.removeCompetitor(request.getData());
        LOGGER.info("[CompetitorHandler] removeCompetitor() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
