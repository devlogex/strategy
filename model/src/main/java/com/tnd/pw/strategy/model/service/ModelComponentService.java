package com.tnd.pw.strategy.model.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.model.entity.ModelComponent;
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ModelComponentService {
    ModelComponent create(Long modelId, String name, String color, String description, String files) throws DBServiceException;
    void update(ModelComponent entity) throws DBServiceException;
    List<ModelComponent> get(ModelComponent entity) throws DBServiceException, ModelComponentNotFoundException;
    void remove(Long id, Long modelId) throws DBServiceException;
}
