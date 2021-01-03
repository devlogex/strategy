package com.tnd.pw.strategy.goal.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.goal.entity.Goal;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;

import java.io.IOException;
import java.util.List;

public interface GoalService {
    Goal create(Long productId) throws DBServiceException;
    void update(Goal entity) throws DBServiceException;
    List<Goal> get(Goal entity) throws DBServiceException, GoalNotFoundException;
    List<Goal> get(List<Long> goalIds) throws DBServiceException, GoalNotFoundException;
    void remove(Long goalId) throws DBServiceException;

    List<String> getTimeFrames(Long productId) throws DBServiceException;
}
