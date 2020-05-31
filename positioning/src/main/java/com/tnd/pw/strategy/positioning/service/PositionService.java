package com.tnd.pw.strategy.positioning.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.positioning.entity.Position;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PositionService {
    Position create(Long workspaceId, Integer type) throws IOException, DBServiceException;
    void update(Position entity) throws IOException, DBServiceException;
    List<Position> get(Long id, Long workspaceId, Integer type, String timeFrame) throws IOException, DBServiceException, PositionNotFoundException;
}
