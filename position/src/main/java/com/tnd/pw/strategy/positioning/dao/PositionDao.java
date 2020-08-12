package com.tnd.pw.strategy.positioning.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.positioning.entity.Position;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PositionDao {
    void create(Position entity) throws IOException, DBServiceException;
    void update(Position entity) throws IOException, DBServiceException;
    List<Position> get(Position entity) throws IOException, DBServiceException, PositionNotFoundException;
    void remove(Long positionId) throws IOException, DBServiceException;

    List<String> getBuzTypes(Long productId) throws IOException, DBServiceException;

    List<String> getTimeFrames(Long productId) throws IOException, DBServiceException;
}
