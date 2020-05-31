package com.tnd.pw.strategy.model.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.model.entity.ModelComponent;
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ModelComponentService {
    ModelComponent create(Long modelId) throws IOException, DBServiceException;
    void update(ModelComponent entity) throws IOException, DBServiceException;
    List<ModelComponent> get(Long id, Long modelId) throws IOException, DBServiceException, ModelComponentNotFoundException;
}
