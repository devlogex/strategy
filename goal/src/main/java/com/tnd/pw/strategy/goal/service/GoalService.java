package com.tnd.pw.strategy.goal.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.goal.entity.Goal;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;

import java.io.IOException;
import java.util.List;

public interface GoalService {
    Goal create(Long workspaceId) throws IOException, DBServiceException;
    void update(Goal entity) throws IOException, DBServiceException;
    List<Goal> get(Goal entity) throws IOException, DBServiceException, GoalNotFoundException;
    void remove(Long goalId) throws IOException, DBServiceException;
}
