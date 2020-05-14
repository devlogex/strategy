package com.tnd.pw.strategy.vision.service.impl;


import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.dao.VisionComponentDao;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.service.VisionComponentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class VisionComponentServiceImpl implements VisionComponentService {
    @Autowired
    private VisionComponentDao visionComponentDao;

    @Override
    public VisionComponent create(Long id, Long visionId) throws IOException, DBServiceException {
        VisionComponent entity = new VisionComponent();
        entity.setId(id);
        entity.setVisionId(visionId);
        entity.setSummary("");
        entity.setColor("#df2222");
        entity.setDescription("");
        entity.setFiles("");
        visionComponentDao.create(entity);
        return entity;
    }

    @Override
    public VisionComponent update(VisionComponent entity) throws IOException, DBServiceException {
        visionComponentDao.update(entity);
        return entity;
    }

    @Override
    public VisionComponent getById(Long id) throws IOException, DBServiceException, VisionComponentNotFoundException {
        VisionComponent entity = new VisionComponent();
        entity.setId(id);
        return visionComponentDao.get(entity).get(0);
    }

    @Override
    public List<VisionComponent> getByVisionId(Long visionId) throws DBServiceException, IOException, VisionComponentNotFoundException {
        VisionComponent entity = new VisionComponent();
        entity.setVisionId(visionId);
        return visionComponentDao.get(entity);
    }
}
