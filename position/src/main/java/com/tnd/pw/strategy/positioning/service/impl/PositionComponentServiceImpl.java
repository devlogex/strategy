package com.tnd.pw.strategy.positioning.service.impl;


import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.positioning.dao.PositionComponentDao;
import com.tnd.pw.strategy.positioning.entity.Position;
import com.tnd.pw.strategy.positioning.entity.PositionComponent;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;
import com.tnd.pw.strategy.positioning.service.PositionComponentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class PositionComponentServiceImpl implements PositionComponentService {
    @Autowired
    private PositionComponentDao positionComponentDao;

    @Override
    public PositionComponent create(Long positionId, String name, String color, String description, String files) throws IOException, DBServiceException {
        PositionComponent entity = new PositionComponent();
        entity.setId(System.currentTimeMillis());
        entity.setPositionId(positionId);
        entity.setColor(color == null ? "#E9644F": color);
        entity.setDescription(description == null ? "": description);
        entity.setName(name == null ? "Component Name": name);
        entity.setFiles(files == null ? "" : files);
        positionComponentDao.create(entity);
        return entity;
    }

    @Override
    public void update(PositionComponent entity) throws IOException, DBServiceException {
        positionComponentDao.update(entity);
    }

    @Override
    public List<PositionComponent> get(Long id, Long modelId) throws IOException, DBServiceException, PositionComponentNotFoundException {
        PositionComponent entity = new PositionComponent();
        entity.setId(id);
        entity.setPositionId(modelId);
        return positionComponentDao.get(entity);
    }

    @Override
    public void remove(Long id, Long positionId) throws IOException, DBServiceException {
        PositionComponent entity = new PositionComponent();
        entity.setId(id);
        entity.setPositionId(positionId);
        positionComponentDao.remove(entity);
    }
}
