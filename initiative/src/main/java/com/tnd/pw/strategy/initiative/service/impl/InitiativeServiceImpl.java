package com.tnd.pw.strategy.initiative.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.initiative.dao.InitiativeDao;
import com.tnd.pw.strategy.initiative.entity.Initiative;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import com.tnd.pw.strategy.initiative.service.InitiativeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class InitiativeServiceImpl implements InitiativeService {
    @Autowired
    private InitiativeDao initiativeDao;

    @Override
    public Initiative create(Long workspaceId) throws IOException, DBServiceException {
        Initiative entity = new Initiative();
        entity.setId(System.currentTimeMillis());
        entity.setWorkspaceId(workspaceId);
        entity.setName("Name...");
        entity.setDescription("");
        entity.setFiles("");
        entity.setParentInitiative("");
        entity.setStatus(0);
        entity.setTimeFrame("");
        entity.setColor("#5DBF40");
        initiativeDao.create(entity);
        return entity;
    }

    @Override
    public void update(Initiative entity) throws IOException, DBServiceException {
        initiativeDao.update(entity);
    }

    @Override
    public List<Initiative> get(Initiative entity) throws IOException, DBServiceException, InitiativeNotFoundException {
        return initiativeDao.get(entity);
    }

    @Override
    public void remove(Long initiativeId) throws IOException, DBServiceException {
        initiativeDao.remove(initiativeId);
    }
}
