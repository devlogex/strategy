package com.tnd.pw.strategy.positioning.service.impl;


import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.positioning.dao.PositionDao;
import com.tnd.pw.strategy.positioning.entity.Position;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;
import com.tnd.pw.strategy.positioning.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionDao positionDao;

    @Override
    public Position create(Long workspaceId, Integer type) throws IOException, DBServiceException {
        Position entity = new Position();
        entity.setId(System.currentTimeMillis());
        entity.setWorkspaceId(workspaceId);
        entity.setName("Model Name");
        entity.setType(type);
        entity.setTimeFrame("");
        entity.setDescription("");
        entity.setFiles("");
        positionDao.create(entity);
        return entity;
    }

    @Override
    public void update(Position entity) throws IOException, DBServiceException {
        positionDao.update(entity);
    }

    @Override
    public List<Position> get(Long id, Long workspaceId, Integer type, String timeFrame) throws IOException, DBServiceException, PositionNotFoundException {
        Position entity = new Position();
        entity.setId(id);
        entity.setWorkspaceId(workspaceId);
        entity.setType(type);
        entity.setTimeFrame(timeFrame);
        return positionDao.get(entity);
    }
}
