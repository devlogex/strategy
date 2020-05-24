package com.tnd.pw.strategy.vision.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.dao.VisionDao;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import com.tnd.pw.strategy.vision.service.VisionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class VisionServiceImpl implements VisionService {
    @Autowired
    private VisionDao visionDao;


    @Override
    public Vision create( Long workspaceId) throws IOException, DBServiceException {
        Vision entity = new Vision();
        entity.setId(System.currentTimeMillis());
        entity.setWorkspaceId(workspaceId);
        entity.setFiles("");
        entity.setDescription("");
        visionDao.create(entity);
        return entity;
    }

    @Override
    public Vision update(Vision entity) throws IOException, DBServiceException {
        visionDao.update(entity);
        return entity;
    }

    @Override
    public Vision getById(Long id) throws IOException, DBServiceException, VisionNotFoundException {
        Vision entity = new Vision();
        entity.setId(id);
        return visionDao.get(entity).get(0);
    }

    @Override
    public Vision getByWorkspaceId(Long workspaceId) throws DBServiceException, VisionNotFoundException, IOException {
        Vision entity = new Vision();
        entity.setWorkspaceId(workspaceId);
        return visionDao.get(entity).get(0);
    }
}
