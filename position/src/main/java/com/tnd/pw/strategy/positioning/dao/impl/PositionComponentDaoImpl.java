package com.tnd.pw.strategy.positioning.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.positioning.dao.PositionComponentDao;
import com.tnd.pw.strategy.positioning.entity.PositionComponent;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class PositionComponentDaoImpl implements PositionComponentDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO position_component(id, name, position_id, color, description, files) " +
                    "values(%d, '%s', %d, '%s', '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE position_component SET name = '%s', color = '%s', description = '%s', files = '%s' WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM position_component WHERE id = %d";
    private static final String SQL_SELECT_BY_MODEL_ID =
            "SELECT * FROM position_component WHERE position_id = %d";
    private static final String SQL_DELETE_BY_ID =
            "DELETE FROM position_component WHERE id = %d";
    private static final String SQL_DELETE_BY_MODEL_ID =
            "DELETE FROM position_component WHERE position_id = %d";

    @Override
    public void create(PositionComponent entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getName(),
                entity.getPositionId(), entity.getColor(), entity.getDescription(), entity.getFiles());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(PositionComponent entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(),
                entity.getColor(), entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<PositionComponent> get(PositionComponent entity) throws IOException, DBServiceException, PositionComponentNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_SELECT_BY_MODEL_ID, entity.getPositionId());
        }
        List<PositionComponent> entities = dataHelper.querySQL(query, PositionComponent.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new PositionComponentNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(PositionComponent entity) throws IOException, DBServiceException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_DELETE_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_DELETE_BY_MODEL_ID, entity.getPositionId());
        }
        dataHelper.executeSQL(query);
    }
}
