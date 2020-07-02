package com.tnd.pw.strategy.model.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.model.entity.ModelComponent;
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ModelComponentDao {
    void create(ModelComponent entity) throws IOException, DBServiceException;
    void update(ModelComponent entity) throws IOException, DBServiceException;
    List<ModelComponent> get(ModelComponent entity) throws IOException, DBServiceException, ModelComponentNotFoundException;
    void remove(ModelComponent entity) throws IOException, DBServiceException;
}
