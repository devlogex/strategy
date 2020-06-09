package com.tnd.pw.strategy.model.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.model.dao.ModelComponentDao;
import com.tnd.pw.strategy.model.entity.ModelComponent;
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class ModelComponentDaoImpl  implements ModelComponentDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO model_component(id, name, model_id, color, description, files) " +
                    "values(%d, '%s', %d, '%s', '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE model_component SET name = '%s', color = '%s', description = '%s', files = '%s' WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM model_component WHERE id = %d";
    private static final String SQL_SELECT_BY_MODEL_ID =
            "SELECT * FROM model_component WHERE model_id = %d";
    private static final String SQL_DELETE_BY_ID =
            "DELETE FROM model_component WHERE id = %d";
    private static final String SQL_DELETE_BY_MODEL_ID =
            "DELETE FROM model_component WHERE model_id = %d";

    @Override
    public void create(ModelComponent entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getName(),
                entity.getModelId(), entity.getColor(), entity.getDescription(), entity.getFiles());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(ModelComponent entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(),
                entity.getColor(), entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<ModelComponent> get(ModelComponent entity) throws IOException, DBServiceException, ModelComponentNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_SELECT_BY_MODEL_ID, entity.getModelId());
        }
        List<ModelComponent> entities = dataHelper.querySQL(query, ModelComponent.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new ModelComponentNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(ModelComponent entity) throws IOException, DBServiceException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_DELETE_BY_ID, entity.getId());
        }
        else {
            query = String.format(SQL_DELETE_BY_ID, entity.getModelId());
        }
        dataHelper.executeSQL(query);
    }
}
