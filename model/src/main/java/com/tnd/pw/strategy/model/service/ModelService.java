package com.tnd.pw.strategy.model.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.model.entity.Model;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ModelService {
    Model create(Long productId, Integer type) throws DBServiceException;
    void update(Model entity) throws DBServiceException;
    List<Model> get(Model entity) throws DBServiceException, ModelNotFoundException;
    void remove(Long modelId) throws DBServiceException;

    List<String> getTimeFrames(Long productId) throws DBServiceException;
}
