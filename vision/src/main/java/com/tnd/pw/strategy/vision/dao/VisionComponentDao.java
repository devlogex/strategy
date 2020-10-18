package com.tnd.pw.strategy.vision.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface VisionComponentDao {
    void create(VisionComponent entity) throws DBServiceException;
    void update(VisionComponent entity) throws DBServiceException;
    List<VisionComponent> get(VisionComponent entity) throws DBServiceException, VisionComponentNotFoundException;
    void remove(VisionComponent entity) throws DBServiceException;
}
