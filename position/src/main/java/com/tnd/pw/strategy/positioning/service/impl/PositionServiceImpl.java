package com.tnd.pw.strategy.positioning.service.impl;


import com.tnd.common.api.common.Utils.GenUID;
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
    public Position create(Long productId) throws IOException, DBServiceException {
        Position entity = new Position();
        entity.setId(GenUID.genIdByProduct(productId));
        entity.setProductId(productId);
        entity.setName("Position Name");
        entity.setBuzType("Business");
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
    public List<Position> get(Position entity) throws IOException, DBServiceException, PositionNotFoundException {
        return positionDao.get(entity);
    }

    @Override
    public void remove(Long positionId) throws IOException, DBServiceException {
        positionDao.remove(positionId);
    }

    @Override
    public List<String> getBuzTypes(Long productId) throws IOException, DBServiceException {
        return positionDao.getBuzTypes(productId);
    }

    @Override
    public List<String> getTimeFrames(Long productId) throws IOException, DBServiceException {
        return positionDao.getTimeFrames(productId);
    }
}
