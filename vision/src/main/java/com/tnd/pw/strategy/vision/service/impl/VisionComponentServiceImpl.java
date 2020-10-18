package com.tnd.pw.strategy.vision.service.impl;


import com.tnd.common.api.common.Utils.GenUID;
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
    public VisionComponent create(Long visionId, String name, String summary, String color, String description, String files) throws DBServiceException {
        VisionComponent entity = new VisionComponent();
        entity.setId(GenUID.genIdByParent(visionId));
        entity.setName(name == null ? "Strategy..." : name);
        entity.setVisionId(visionId);
        entity.setSummary(summary == null ? "Summary..." : summary);
        entity.setColor(color == null ? "#E9644F" : color);
        entity.setDescription(description == null ? "" : description);
        entity.setFiles(files == null ? "" : files);
        visionComponentDao.create(entity);
        return entity;
    }

    @Override
    public VisionComponent update(VisionComponent entity) throws DBServiceException {
        visionComponentDao.update(entity);
        return entity;
    }

    @Override
    public VisionComponent getById(Long id) throws DBServiceException, VisionComponentNotFoundException {
        VisionComponent entity = new VisionComponent();
        entity.setId(id);
        return visionComponentDao.get(entity).get(0);
    }

    @Override
    public List<VisionComponent> getByVisionId(Long visionId) throws DBServiceException, VisionComponentNotFoundException {
        VisionComponent entity = new VisionComponent();
        entity.setVisionId(visionId);
        return visionComponentDao.get(entity);
    }

    @Override
    public void remove(Long id) throws DBServiceException {
        VisionComponent entity = new VisionComponent();
        entity.setId(id);
        visionComponentDao.remove(entity);
    }
}
