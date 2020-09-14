package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.call.api.exceptions.CallApiFailException;
import com.tnd.pw.strategy.common.representations.FilterInfoRepresentation;
import com.tnd.pw.strategy.common.representations.GoalRepresentation;
import com.tnd.pw.strategy.common.representations.ListGoalRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;

import java.io.IOException;

public interface GoalServiceHandler extends ServiceHandler {
    ListGoalRepresentation addGoal(StrategyRequest request) throws IOException, DBServiceException, GoalNotFoundException;
    GoalRepresentation updateGoal(StrategyRequest request) throws DBServiceException, IOException, GoalNotFoundException, CallApiFailException;
    ListGoalRepresentation getGoal(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException;
    GoalRepresentation getGoalInfo(StrategyRequest request) throws IOException, CallApiFailException, DBServiceException, GoalNotFoundException;
    ListGoalRepresentation removeGoal(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, GoalNotFoundException;

    FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws IOException, DBServiceException;
}
