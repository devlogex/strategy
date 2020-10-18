package com.tnd.pw.strategy.positioning.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.positioning.entity.Position;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PositionDao {
    void create(Position entity) throws DBServiceException;
    void update(Position entity) throws DBServiceException;
    List<Position> get(Position entity) throws DBServiceException, PositionNotFoundException;
    void remove(Long positionId) throws DBServiceException;

    List<String> getBuzTypes(Long productId) throws DBServiceException;

    List<String> getTimeFrames(Long productId) throws DBServiceException;
}
