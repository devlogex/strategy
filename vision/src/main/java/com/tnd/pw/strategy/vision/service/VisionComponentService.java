package com.tnd.pw.strategy.vision.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface VisionComponentService {
    VisionComponent create(Long visionId, String name, String summary, String color, String description, String files) throws DBServiceException;
    VisionComponent update(VisionComponent entity) throws DBServiceException;
    VisionComponent getById(Long id) throws DBServiceException, VisionComponentNotFoundException;
    List<VisionComponent> getByVisionId(Long visionId) throws DBServiceException, VisionComponentNotFoundException;
    void remove(Long id) throws DBServiceException;
}
