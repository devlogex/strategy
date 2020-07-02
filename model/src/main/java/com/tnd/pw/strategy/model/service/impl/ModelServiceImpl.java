package com.tnd.pw.strategy.model.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.model.dao.ModelDao;
import com.tnd.pw.strategy.model.entity.Model;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;
import com.tnd.pw.strategy.model.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class ModelServiceImpl implements ModelService {
    @Autowired
    private ModelDao modelDao;

    @Override
    public Model create(Long productId, Integer type) throws IOException, DBServiceException {
        Model entity = new Model();
        entity.setId(System.currentTimeMillis());
        entity.setProductId(productId);
        entity.setName("Model Name");
        entity.setType(type);
        entity.setTimeFrame("");
        entity.setDescription("");
        entity.setFiles("");
        entity.setBuzType("Business");
        modelDao.create(entity);
        return entity;
    }

    @Override
    public void update(Model entity) throws IOException, DBServiceException {
        modelDao.update(entity);
    }

    @Override
    public List<Model> get(Model entity) throws IOException, DBServiceException, ModelNotFoundException {
        return modelDao.get(entity);
    }

    @Override
    public void remove(Long modelId) throws IOException, DBServiceException {
        modelDao.remove(modelId);
    }
}
