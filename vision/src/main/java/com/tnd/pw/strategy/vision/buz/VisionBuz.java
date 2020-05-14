package com.tnd.pw.strategy.vision.buz;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;

import java.io.IOException;
import java.util.List;

public interface VisionBuz {
    Vision addVision(Long workspaceId) throws IOException, DBServiceException;
    Vision updateVision(Vision vision) throws IOException, DBServiceException;
    Vision getVisionById(Long visionId) throws DBServiceException, VisionNotFoundException, IOException;
    Vision getVisionByWorkspaceId(Long workspaceId) throws DBServiceException, VisionNotFoundException, IOException;
    
    VisionComponent addComponent(Long visionId) throws IOException, DBServiceException;
    VisionComponent updateComponent(VisionComponent visionComponent) throws IOException, DBServiceException;
    VisionComponent getComponentById(Long componentId) throws DBServiceException, IOException, VisionComponentNotFoundException;
    List<VisionComponent> getComponentByVisionId(Long visionId) throws DBServiceException, IOException, VisionComponentNotFoundException;
}
