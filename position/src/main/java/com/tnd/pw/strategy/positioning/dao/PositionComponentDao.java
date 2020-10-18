package com.tnd.pw.strategy.positioning.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.positioning.entity.PositionComponent;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PositionComponentDao {
    void create(PositionComponent entity) throws DBServiceException;
    void update(PositionComponent entity) throws DBServiceException;
    List<PositionComponent> get(PositionComponent entity) throws DBServiceException, PositionComponentNotFoundException;
    void remove(PositionComponent entity) throws DBServiceException;
}
