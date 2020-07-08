package com.tnd.pw.strategy.goal.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.goal.dao.GoalDao;
import com.tnd.pw.strategy.goal.entity.Goal;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class GoalDaoImpl implements GoalDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO goal(id, product_id, name, description, files, parent_goal, " +
                    "status, time_frame, color, metric, metric_description, metric_file) " +
                    "values(%d, %d, '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE goal SET name = '%s', description = '%s', files = '%s', parent_goal = '%s'" +
                    ", status = '%s', time_frame = '%s', color = '%s', metric = '%s'" +
                    ", metric_description = '%s', metric_file = '%s'" +
                    " WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM goal WHERE id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM goal WHERE product_id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID_STATUS =
            "SELECT * FROM goal WHERE product_id = %d AND status = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME =
            "SELECT * FROM goal WHERE product_id = %d AND time_frame = '%s'";
    private static final String SQL_SELECT_BY_PRODUCT_ID_STATUS_TIME_FRAME =
            "SELECT * FROM goal WHERE product_id = %d AND status = %d AND time_frame = '%s'";
    private static final String SQL_DELETE =
            "DELETE FROM goal WHERE id = %d";
    @Override
    public void create(Goal entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(),
                entity.getName(), entity.getDescription(), entity.getFiles(),
                entity.getParentGoal(), entity.getStatus(), entity.getTimeFrame(),
                entity.getColor(), entity.getMetric(), entity.getMetricDescription(), entity.getMetricFile());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(Goal entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(),entity.getDescription(),
                entity.getFiles(), entity.getParentGoal(), entity.getStatus(),
                entity.getTimeFrame(), entity.getColor(), entity.getMetric(),
                entity.getMetricDescription(), entity.getMetricFile(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<Goal> get(Goal entity) throws IOException, DBServiceException, GoalNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getStatus() != null && entity.getTimeFrame() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID_STATUS_TIME_FRAME,
                    entity.getProductId(), entity.getStatus(), entity.getTimeFrame());
        }
        else if(entity.getStatus() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID_STATUS,
                    entity.getProductId(), entity.getStatus());
        }
        else if(entity.getTimeFrame() != null) {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME,
                    entity.getProductId(), entity.getTimeFrame());
        }
        else {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID, entity.getProductId());
        }
        List<Goal> entities = dataHelper.querySQL(query, Goal.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new GoalNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(Long goalId) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, goalId);
        dataHelper.executeSQL(query);
    }
}
