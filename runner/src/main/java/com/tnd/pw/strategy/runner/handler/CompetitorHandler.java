package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;
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

    @HandlerService(path = "/strategy/competitor/add", protocol = "POST")
    public BaseResponse<ListCompetitorRepresentation> addCompetitor(StrategyRequest request) throws IOException, DBServiceException, CompetitorNotFoundException {
        LOGGER.info("[CompetitorHandler] addCompetitor() - request: {}", GsonUtils.convertToString(request));
        ListCompetitorRepresentation response = competitorServiceHandler.addCompetitor(request);
        LOGGER.info("[CompetitorHandler] addCompetitor() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/competitor/update", protocol = "POST")
    public BaseResponse<CompetitorRepresentation> updateCompetitor(StrategyRequest request) throws DBServiceException, IOException, CompetitorNotFoundException, CallApiFailException {
        LOGGER.info("[CompetitorHandler] updateCompetitor() - request: {}", GsonUtils.convertToString(request));
        CompetitorRepresentation response = competitorServiceHandler.updateCompetitor(request);
        LOGGER.info("[CompetitorHandler] updateCompetitor() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/competitor", protocol = "GET")
    public BaseResponse<ListCompetitorRepresentation> getCompetitor(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[CompetitorHandler] getCompetitor() - request: {}", GsonUtils.convertToString(request));
        ListCompetitorRepresentation response = competitorServiceHandler.getCompetitor(request);
        LOGGER.info("[CompetitorHandler] getCompetitor() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/competitor/info", protocol = "GET")
    public BaseResponse<CompetitorRepresentation> getCompetitorInfo(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, CompetitorNotFoundException, CallApiFailException {
        LOGGER.info("[CompetitorHandler] getCompetitorInfo() - request: {}", GsonUtils.convertToString(request));
        CompetitorRepresentation response = competitorServiceHandler.getCompetitorInfo(request);
        LOGGER.info("[CompetitorHandler] getCompetitorInfo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/competitor/delete", protocol = "POST")
    public BaseResponse<ListCompetitorRepresentation> removeCompetitor(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException, CompetitorNotFoundException {
        LOGGER.info("[CompetitorHandler] removeCompetitor() - request: {}", GsonUtils.convertToString(request));
        ListCompetitorRepresentation response = competitorServiceHandler.removeCompetitor(request);
        LOGGER.info("[CompetitorHandler] removeCompetitor() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
