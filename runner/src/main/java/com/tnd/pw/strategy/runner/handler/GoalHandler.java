package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.GoalRepresentation;
import com.tnd.pw.strategy.common.representations.ListGoalRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.service.GoalServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class GoalHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoalHandler.class);
    @Autowired
    private GoalServiceHandler goalServiceHandler;

    @HandlerService(path = "/strategy/goal/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListGoalRepresentation> addGoal(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, GoalNotFoundException {
        LOGGER.info("[GoalHandler] addGoal() - request: {}", GsonUtils.convertToString(request));
        ListGoalRepresentation response = goalServiceHandler.addGoal(request.getData());
        LOGGER.info("[GoalHandler] addGoal() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/goal/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<GoalRepresentation> updateGoal(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, GoalNotFoundException {
        LOGGER.info("[GoalHandler] updateGoal() - request: {}", GsonUtils.convertToString(request));
        GoalRepresentation response = goalServiceHandler.updateGoal(request.getData());
        LOGGER.info("[GoalHandler] updateGoal() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/goal", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListGoalRepresentation> getGoal(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[GoalHandler] getGoal() - request: {}", GsonUtils.convertToString(request));
        ListGoalRepresentation response = goalServiceHandler.getGoal(request.getData());
        LOGGER.info("[GoalHandler] getGoal() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/goal/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListGoalRepresentation> removeGoal(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, GoalNotFoundException {
        LOGGER.info("[GoalHandler] removeGoal() - request: {}", GsonUtils.convertToString(request));
        ListGoalRepresentation response = goalServiceHandler.removeGoal(request.getData());
        LOGGER.info("[GoalHandler] removeGoal() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
