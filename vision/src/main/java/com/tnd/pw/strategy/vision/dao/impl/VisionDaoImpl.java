package com.tnd.pw.strategy.vision.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.vision.dao.VisionDao;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class VisionDaoImpl implements VisionDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO vision(id, workspace_id, description, files) values(%d, %d, '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE vision SET description = '%s', files = '%s' WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM vision where id = %d";
    private static final String SQL_SELECT_BY_WORKSPACE_ID =
            "SELECT * FROM vision where workspace_id = %d";


    @Override
    public void create(Vision entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getWorkspaceId(), entity.getDescription(), entity.getFiles());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(Vision entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<Vision> get(Vision entity) throws IOException, DBServiceException, VisionNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getWorkspaceId() != null) {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID, entity.getWorkspaceId());
        }
        List<Vision> entities = dataHelper.querySQL(query, Vision.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new VisionNotFoundException();
        }
        return entities;
    }
}
