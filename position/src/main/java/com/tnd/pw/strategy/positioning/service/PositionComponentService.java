package com.tnd.pw.strategy.positioning.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.positioning.entity.PositionComponent;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface PositionComponentService {
    PositionComponent create(Long positionId, String name, String color, String description, String files) throws IOException, DBServiceException;
    void update(PositionComponent entity) throws IOException, DBServiceException;
    List<PositionComponent> get(Long id, Long positionId) throws IOException, DBServiceException, PositionComponentNotFoundException;
    void remove(Long id, Long positionId) throws IOException, DBServiceException;
}
