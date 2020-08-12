package com.tnd.pw.strategy.model.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.model.entity.Model;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;

import java.io.IOException;
import java.util.List;

public interface ModelDao {
    void create(Model entity) throws IOException, DBServiceException;
    void update(Model entity) throws IOException, DBServiceException;
    List<Model> get(Model entity) throws IOException, DBServiceException, ModelNotFoundException;

    void remove(Long modelId) throws IOException, DBServiceException;

    List<String> getTimeFrames(Long productId) throws IOException, DBServiceException;
}
