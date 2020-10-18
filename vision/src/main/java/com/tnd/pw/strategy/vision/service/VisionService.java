package com.tnd.pw.strategy.vision.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;

import java.io.IOException;
import java.util.List;

public interface VisionService {
    Vision create(Long productId) throws DBServiceException;
    Vision update(Vision entity) throws DBServiceException;
    List<Vision> get(Vision entity) throws DBServiceException, VisionNotFoundException;

}
