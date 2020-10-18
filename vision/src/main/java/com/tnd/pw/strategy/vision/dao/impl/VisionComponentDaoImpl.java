package com.tnd.pw.strategy.vision.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.vision.dao.VisionComponentDao;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class VisionComponentDaoImpl implements VisionComponentDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE = "INSERT INTO vision_component(id, vision_id, name, summary, color, description, files) values(%d, %d, '%s', '%s', '%s', '%s', '%s')";
    private static final String SQL_UPDATE = "UPDATE vision_component SET name = '%s', summary = '%s', color = '%s', description = '%s', files = '%s' WHERE id = '%s'";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM vision_component where id = %d";
    private static final String SQL_SELECT_BY_VISION_ID = "SELECT * FROM vision_component where vision_id = %d";
    private static final String SQL_DELETE = "DELETE FROM vision_component WHERE id = %d";

    @Override
    public void create(VisionComponent entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getVisionId(), entity.getName(), entity.getSummary(), entity.getColor(), entity.getDescription(), entity.getFiles());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(VisionComponent entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getSummary(), entity.getColor(), entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<VisionComponent> get(VisionComponent entity) throws DBServiceException, VisionComponentNotFoundException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getVisionId() != null) {
            query = String.format(SQL_SELECT_BY_VISION_ID, entity.getVisionId());
        }
        List<VisionComponent> entities = dataHelper.querySQL(query, VisionComponent.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new VisionComponentNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(VisionComponent entity) throws DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }
}
