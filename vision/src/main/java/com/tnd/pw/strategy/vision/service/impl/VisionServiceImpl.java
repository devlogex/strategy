package com.tnd.pw.strategy.vision.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.dao.VisionDao;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import com.tnd.pw.strategy.vision.service.VisionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class VisionServiceImpl implements VisionService {
    @Autowired
    private VisionDao visionDao;


    @Override
    public Vision create( Long productId) throws DBServiceException {
        Vision entity = new Vision();
        entity.setId(GenUID.genIdByProduct(productId));
        entity.setProductId(productId);
        entity.setFiles("");
        entity.setDescription("");
        visionDao.create(entity);
        return entity;
    }

    @Override
    public Vision update(Vision entity) throws DBServiceException {
        visionDao.update(entity);
        return entity;
    }

    @Override
    public List<Vision> get(Vision entity) throws DBServiceException, VisionNotFoundException {
        return visionDao.get(entity);
    }

}
