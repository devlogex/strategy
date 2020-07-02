package com.tnd.pw.strategy.initiative.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.initiative.dao.InitiativeDao;
import com.tnd.pw.strategy.initiative.entity.Initiative;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class InitiativeDaoImpl  implements InitiativeDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO initiative(id, product_id, name, description, files, parent_initiative, " +
                    "status, time_frame, color) " +
                    "values(%d, %d, '%s', '%s', '%s', '%s', %d, '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE initiative SET name = '%s', description = '%s', files = '%s', parent_initiative = '%s'" +
                    ", status = '%s', time_frame = '%s', color = '%s', start_at = %d" +
                    ", end_at = %d, visible = %d" +
                    " WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM initiative WHERE id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM initiative WHERE product_id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID_STATUS =
            "SELECT * FROM initiative WHERE product_id = %d AND status = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME =
            "SELECT * FROM initiative WHERE product_id = %d AND time_frame = '%s'";
    private static final String SQL_SELECT_BY_PRODUCT_ID_STATUS_TIME_FRAME =
            "SELECT * FROM initiative WHERE product_id = %d AND status = %d AND time_frame = '%s'";
    private static final String SQL_DELETE =
            "DELETE FROM initiative WHERE id = %d";
    @Override
    public void create(Initiative entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(),
                entity.getName(), entity.getDescription(), entity.getFiles(), entity.getParentInitiative(),
                entity.getStatus(), entity.getTimeFrame(), entity.getColor());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(Initiative entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(),entity.getDescription(),
                entity.getFiles(), entity.getParentInitiative(), entity.getStatus(),
                entity.getTimeFrame(), entity.getColor(), entity.getStartAt(),
                entity.getEndAt(), entity.getVisible(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<Initiative> get(Initiative entity) throws IOException, DBServiceException, InitiativeNotFoundException {
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
        List<Initiative> entities = dataHelper.querySQL(query, Initiative.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new InitiativeNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(Long initiativeId) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, initiativeId);
        dataHelper.executeSQL(query);
    }
}
