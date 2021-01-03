package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.FilterInfoRepresentation;
import com.tnd.pw.strategy.common.representations.GoalRep;
import com.tnd.pw.strategy.common.representations.ListGoalRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;

public interface GoalServiceHandler extends ServiceHandler {
    ListGoalRepresentation addGoal(StrategyRequest request) throws DBServiceException, GoalNotFoundException;
    GoalRep updateGoal(StrategyRequest request) throws DBServiceException, GoalNotFoundException, ActionServiceFailedException;
    ListGoalRepresentation getGoal(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    GoalRep getGoalInfo(StrategyRequest request) throws DBServiceException, GoalNotFoundException, ActionServiceFailedException;
    ListGoalRepresentation removeGoal(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, GoalNotFoundException;

    FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws DBServiceException;
}
