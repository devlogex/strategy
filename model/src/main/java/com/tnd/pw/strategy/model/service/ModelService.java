package com.tnd.pw.strategy.model.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.model.entity.Model;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ModelService {
    Model create(Long workspaceId, Integer type) throws IOException, DBServiceException;
    void update(Model entity) throws IOException, DBServiceException;
    List<Model> get(Long id, Long workspaceId, Integer type, String timeFrame) throws IOException, DBServiceException, ModelNotFoundException;
}
