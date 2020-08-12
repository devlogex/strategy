package com.tnd.pw.strategy.model.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.dbservice.DataHelper;
import com.tnd.pw.strategy.model.dao.ModelDao;
import com.tnd.pw.strategy.model.entity.Model;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModelDaoImpl implements ModelDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO model(id, product_id, name, type, buz_type, time_frame, description, files) " +
                    "values(%d, %d, '%s', %d, '%s', '%s', '%s', '%s')";
    private static final String SQL_UPDATE =
            "UPDATE model SET name = '%s', time_frame = '%s', buz_type = '%s', description = '%s', files = '%s' WHERE id = %d";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM model WHERE id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID =
            "SELECT * FROM model WHERE product_id = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID_TYPE =
            "SELECT * FROM model WHERE product_id = %d AND type = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME =
            "SELECT * FROM model WHERE product_id = %d AND time_frame = '%s'";
    private static final String SQL_SELECT_BY_PRODUCT_ID_TYPE_TIME_FRAME =
            "SELECT * FROM model WHERE product_id = %d AND type = %d AND time_frame = '%s'";
    private static final String SQL_SELECT_BY_PRODUCT_ID_BUZ_TYPE =
            "SELECT * FROM model WHERE product_id = %d AND buz_type = '%s'";
    private static final String SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME_BUZ_TYPE =
            "SELECT * FROM model WHERE product_id = %d AND time_frame = '%s' AND buz_type = '%s'";
    private static final String SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME_BUZ_TYPE_TYPE =
            "SELECT * FROM model WHERE product_id = %d AND time_frame = '%s' AND buz_type = '%s' AND type = %d";
    private static final String SQL_SELECT_BY_PRODUCT_ID_BUZ_TYPE_TYPE =
            "SELECT * FROM model WHERE product_id = %d AND buz_type = '%s' AND type = %d";
    private static final String SQL_DELETE =
            "DELETE FROM model WHERE id = %d";

    private static final String SQL_SELECT_TIME_FRAMES =
            "SELECT DISTINCT time_frame FROM model WHERE product_id = %d AND NOT time_frame = ''";

    @Override
    public void create(Model entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getProductId(), entity.getName(),
                entity.getType(), entity.getBuzType(), entity.getTimeFrame(), entity.getDescription(), entity.getFiles());
        dataHelper.executeSQL(query);
    }

    @Override
    public void update(Model entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(),
                entity.getTimeFrame(), entity.getBuzType(), entity.getDescription(), entity.getFiles(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<Model> get(Model entity) throws IOException, DBServiceException, ModelNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getBuzType() != null) {
            if(entity.getTimeFrame() != null && entity.getType() != null) {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME_BUZ_TYPE_TYPE,
                        entity.getProductId(), entity.getTimeFrame(), entity.getBuzType(), entity.getType());
            }
            else if(entity.getTimeFrame() != null) {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME_BUZ_TYPE,
                        entity.getProductId(), entity.getTimeFrame(), entity.getBuzType());
            }
            else if(entity.getType() != null) {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID_BUZ_TYPE_TYPE,
                        entity.getProductId(), entity.getBuzType(), entity.getType());
            }
            else {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID_BUZ_TYPE,
                        entity.getProductId(), entity.getBuzType());
            }
        }
        else if(entity.getTimeFrame() != null) {
            if(entity.getType() != null) {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID_TYPE_TIME_FRAME,
                        entity.getProductId(), entity.getType(), entity.getTimeFrame());
            }
            else {
                query = String.format(SQL_SELECT_BY_PRODUCT_ID_TIME_FRAME,
                        entity.getProductId(), entity.getTimeFrame());
            }
        }
        else if(entity.getType() != null){
            query = String.format(SQL_SELECT_BY_PRODUCT_ID_TYPE,
                    entity.getProductId(), entity.getType());
        }
        else {
            query = String.format(SQL_SELECT_BY_PRODUCT_ID,
                    entity.getProductId());
        }
        List<Model> entities = dataHelper.querySQL(query, Model.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new ModelNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(Long modelId) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, modelId);
        dataHelper.executeSQL(query);
    }

    @Override
    public List<String> getTimeFrames(Long productId) throws IOException, DBServiceException {
        String query = String.format(SQL_SELECT_TIME_FRAMES, productId);
        List<Model> models = dataHelper.querySQL(query, Model.class);
        return models == null ? new ArrayList<>() : models.stream().map(model -> model.getTimeFrame()).collect(Collectors.toList());
    }
}
