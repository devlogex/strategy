package com.tnd.pw.strategy.initiative.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.initiative.entity.Initiative;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;

import java.io.IOException;
import java.util.List;

public interface InitiativeDao {
    void create(Initiative entity) throws IOException, DBServiceException;
    void update(Initiative entity) throws IOException, DBServiceException;
    List<Initiative> get(Initiative entity) throws IOException, DBServiceException, InitiativeNotFoundException;
    void remove(Long initiativeId) throws IOException, DBServiceException;

    List<String> getTimeFrames(Long productId) throws IOException, DBServiceException;
}
