package com.tnd.pw.strategy.model.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.model.dao.ModelComponentDao;
import com.tnd.pw.strategy.model.entity.ModelComponent;
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;
import com.tnd.pw.strategy.model.service.ModelComponentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class ModelComponentServiceImpl implements ModelComponentService {
    @Autowired
    private ModelComponentDao modelComponentDao;

    @Override
    public ModelComponent create(Long modelId) throws IOException, DBServiceException {
        ModelComponent entity = new ModelComponent();
        entity.setId(System.currentTimeMillis());
        entity.setModelId(modelId);
        entity.setColor("#F5F6FA");
        entity.setDescription("");
        entity.setName("Model Component Name");
        entity.setFiles("");
        modelComponentDao.create(entity);
        return entity;
    }

    @Override
    public void update(ModelComponent entity) throws IOException, DBServiceException {
        modelComponentDao.update(entity);
    }

    @Override
    public List<ModelComponent> get(Long id, Long modelId) throws IOException, DBServiceException, ModelComponentNotFoundException {
        ModelComponent entity = new ModelComponent();
        entity.setId(id);
        entity.setModelId(modelId);
        return modelComponentDao.get(entity);
    }
}
