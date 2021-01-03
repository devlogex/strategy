package com.tnd.pw.strategy.initiative.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
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
    public Initiative create(Initiative entity) throws DBServiceException {
        entity.setId(GenUID.genIdByProduct(entity.getProductId()));
        if(entity.getStatus() == null)
            entity.setStatus(0);
        entity.setName("Name...");
        entity.setDescription("");
        entity.setFiles("");
        entity.setParentInitiative("");
        entity.setTimeFrame("");
        entity.setColor("#5DBF40");
        initiativeDao.create(entity);
        return entity;
    }

    @Override
    public void update(Initiative entity) throws DBServiceException {
        initiativeDao.update(entity);
    }

    @Override
    public List<Initiative> get(Initiative entity) throws DBServiceException, InitiativeNotFoundException {
        return initiativeDao.get(entity);
    }

    @Override
    public List<Initiative> get(List<Long> ids) throws DBServiceException, InitiativeNotFoundException {
        return initiativeDao.get(ids);
    }

    @Override
    public void remove(Long initiativeId) throws DBServiceException {
        initiativeDao.remove(initiativeId);
    }

    @Override
    public List<String> getTimeFrames(Long productId) throws DBServiceException {
        return initiativeDao.getTimeFrames(productId);
    }
}
