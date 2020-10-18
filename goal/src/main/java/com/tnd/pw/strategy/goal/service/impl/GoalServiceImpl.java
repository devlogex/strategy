package com.tnd.pw.strategy.goal.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.goal.dao.GoalDao;
import com.tnd.pw.strategy.goal.entity.Goal;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;
import com.tnd.pw.strategy.goal.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class GoalServiceImpl implements GoalService {
    @Autowired
    private GoalDao goalDao;

    @Override
    public Goal create(Long productId) throws DBServiceException {
        Goal entity = new Goal();
        entity.setId(GenUID.genIdByProduct(productId));
        entity.setProductId(productId);
        entity.setName("Name...");
        entity.setDescription("");
        entity.setFiles("");
        entity.setParentGoal("");
        entity.setStatus(0);
        entity.setTimeFrame("");
        entity.setColor("#5DBF40");
        entity.setMetric("+X% market share (for success metric)");
        entity.setMetricDescription("");
        entity.setMetricFile("");
        goalDao.create(entity);
        return entity;
    }

    @Override
    public void update(Goal entity) throws DBServiceException {
        goalDao.update(entity);
    }

    @Override
    public List<Goal> get(Goal entity) throws DBServiceException, GoalNotFoundException {
        return goalDao.get(entity);
    }

    @Override
    public void remove(Long goalId) throws DBServiceException {
        goalDao.remove(goalId);
    }

    @Override
    public List<String> getTimeFrames(Long productId) throws DBServiceException {
        return goalDao.getTimeFrames(productId);
    }
}
