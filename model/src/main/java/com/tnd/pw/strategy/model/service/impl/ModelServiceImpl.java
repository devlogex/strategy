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
    public Model create(Long workspaceId, Integer type) throws IOException, DBServiceException {
        Model entity = new Model();
        entity.setId(System.currentTimeMillis());
        entity.setWorkspaceId(workspaceId);
        entity.setName("Model Name");
        entity.setType(type);
        entity.setTimeFrame("");
        entity.setDescription("");
        entity.setFiles("");
        modelDao.create(entity);
        return entity;
    }

    @Override
    public void update(Model entity) throws IOException, DBServiceException {
        modelDao.update(entity);
    }

    @Override
    public List<Model> get(Long id, Long workspaceId, Integer type, String timeFrame) throws IOException, DBServiceException, ModelNotFoundException {
        Model entity = new Model();
        entity.setId(id);
        entity.setWorkspaceId(workspaceId);
        entity.setType(type);
        entity.setTimeFrame(timeFrame);
        return modelDao.get(entity);
    }
}
