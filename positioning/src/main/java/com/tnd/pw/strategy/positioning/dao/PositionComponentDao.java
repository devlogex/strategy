package com.tnd.pw.strategy.positioning.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.positioning.entity.PositionComponent;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PositionComponentDao {
    void create(PositionComponent entity) throws IOException, DBServiceException;
    void update(PositionComponent entity) throws IOException, DBServiceException;
    List<PositionComponent> get(PositionComponent entity) throws IOException, DBServiceException, PositionComponentNotFoundException;
}
