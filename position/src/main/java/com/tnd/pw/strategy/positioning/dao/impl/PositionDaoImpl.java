package com.tnd.pw.strategy.positioning.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.positioning.dao.PositionDao;
import com.tnd.pw.strategy.positioning.entity.Position;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class PositionDaoImpl implements PositionDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO position(id, workspace_id, name, buz_type, time_frame, description, files) " +
                    "values(%d, %d, '%s', '%s', '%s', '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE position " +
                    "SET name = '%s', time_frame = '%s', buz_type = '%s', description = '%s', files = '%s' " +
                    "WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM position WHERE id = %d";
    private static final String SQL_SELECT_BY_WORKSPACE_ID =
            "SELECT * FROM position WHERE workspace_id = %d";
    private static final String SQL_SELECT_BY_WORKSPACE_ID_BUZ_TYPE =
            "SELECT * FROM position WHERE workspace_id = %d AND buz_type = '%s'";
    private static final String SQL_SELECT_BY_WORKSPACE_ID_TIME_FRAME =
            "SELECT * FROM position WHERE workspace_id = %d AND time_frame = '%s'";
    private static final String SQL_SELECT_BY_WORKSPACE_ID_BUZ_TYPE_TIME_FRAME =
            "SELECT * FROM position WHERE workspace_id = %d AND buz_type = '%s' AND time_frame = '%s'";
    private static final String SQL_DELETE =
            "DELETE FROM position WHERE id = %d";

    @Override
    public void create(Position entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getWorkspaceId(), entity.getName(),
                entity.getBuzType(), entity.getTimeFrame(), entity.getDescription(), entity.getFiles());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(Position entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(),
                entity.getTimeFrame(), entity.getBuzType(), entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<Position> get(Position entity) throws IOException, DBServiceException, PositionNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getTimeFrame() != null && entity.getBuzType() != null) {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID_BUZ_TYPE_TIME_FRAME,
                    entity.getWorkspaceId(), entity.getBuzType(), entity.getTimeFrame());
        }
        else if(entity.getBuzType() != null) {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID_BUZ_TYPE,
                    entity.getWorkspaceId(), entity.getBuzType());
        }
        else if(entity.getTimeFrame() != null) {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID_TIME_FRAME,
                    entity.getWorkspaceId(), entity.getTimeFrame());
        }
        else {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID,
                    entity.getWorkspaceId());
        }
        List<Position> entities = dataHelper.querySQL(query, Position.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new PositionNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(Long positionId) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, positionId);
        dataHelper.executeSQL(query);
    }
}
