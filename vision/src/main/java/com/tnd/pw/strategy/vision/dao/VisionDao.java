package com.tnd.pw.strategy.vision.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;

import java.io.IOException;
import java.util.List;

public interface VisionDao {
    void create(Vision entity) throws DBServiceException;
    void update(Vision entity) throws DBServiceException;
    List<Vision> get(Vision entity) throws DBServiceException, VisionNotFoundException;
}
