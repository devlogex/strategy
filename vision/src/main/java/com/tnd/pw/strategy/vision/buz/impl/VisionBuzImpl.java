package com.tnd.pw.strategy.vision.buz.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.vision.buz.VisionBuz;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import com.tnd.pw.strategy.vision.service.VisionComponentService;
import com.tnd.pw.strategy.vision.service.VisionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class VisionBuzImpl implements VisionBuz {
    @Autowired
    private VisionService visionService;
    @Autowired
    private VisionComponentService visionComponentService;

    @Override
    public Vision addVision(Long workspaceId) throws IOException, DBServiceException {
        return visionService.create(System.currentTimeMillis(), workspaceId);
    }

    @Override
    public Vision updateVision(Vision vision) throws IOException, DBServiceException {
        return visionService.update(vision);
    }

    @Override
    public Vision getVisionById(Long visionId) throws DBServiceException, VisionNotFoundException, IOException {
        return visionService.getById(visionId);
    }

    @Override
    public Vision getVisionByWorkspaceId(Long workspaceId) throws DBServiceException, VisionNotFoundException, IOException {
        return visionService.getByWorkspaceId(workspaceId);
    }

    @Override
    public VisionComponent addComponent(Long visionId) throws IOException, DBServiceException {
        return visionComponentService.create(System.currentTimeMillis(), visionId);
    }

    @Override
    public VisionComponent updateComponent(VisionComponent visionComponent) throws IOException, DBServiceException {
        return visionComponentService.update(visionComponent);
    }

    @Override
    public VisionComponent getComponentById(Long componentId) throws DBServiceException, IOException, VisionComponentNotFoundException {
        return visionComponentService.getById(componentId);
    }

    @Override
    public List<VisionComponent> getComponentByVisionId(Long visionId) throws DBServiceException, IOException, VisionComponentNotFoundException {
        return visionComponentService.getByVisionId(visionId);
    }
}
