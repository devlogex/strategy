package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListVisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.common.requests.VisionRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.vision.buz.VisionBuz;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisionHandlerBuz {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisionHandlerBuz.class);
    @Autowired
    private VisionBuz visionBuz;

    private static int num_comp_default = 9;

    public VisionRepresentation addVision(VisionRequest request) throws IOException, DBServiceException {
        Vision vision = visionBuz.addVision(Long.valueOf(request.getWorkspaceId()));
        List<VisionComponent> visionComponents = new ArrayList<>();

        for(int i=0;i < num_comp_default;i++) {
            VisionComponent visionComponent = visionBuz.addComponent(vision.getId());
            visionComponents.add(visionComponent);
        }
        return RepresentationBuilder.buildVisionRepresentation(vision, visionComponents);
    }

    public VisionRepresentation updateVision(VisionRequest request) throws DBServiceException, VisionNotFoundException, IOException {
        Vision vision = visionBuz.getVisionById(Long.valueOf(request.getVisionId()));
        if(request.getFiles() != null) {
            vision.setFiles(request.getFiles());
        }
        Vision newVision = visionBuz.updateVision(vision);
        List<VisionComponent> visionComponents = null;
        try {
            visionComponents = visionBuz.getComponentByVisionId(newVision.getId());
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with visionId: {}", newVision.getId());
            return RepresentationBuilder.buildVisionRepresentation(newVision, null);
        }
        return RepresentationBuilder.buildVisionRepresentation(newVision, visionComponents);
    }

    public VisionRepresentation getVision(VisionRequest request) throws DBServiceException, IOException {
        Vision vision = new Vision();
        try {
            if (request.getVisionId() != null) {
                vision = visionBuz.getVisionById(Long.valueOf(request.getVisionId()));
            } else {
                vision = visionBuz.getVisionByWorkspaceId(Long.valueOf(request.getWorkspaceId()));
            }
            List<VisionComponent> visionComponents = visionBuz.getComponentByVisionId(vision.getId());
            return RepresentationBuilder.buildVisionRepresentation(vision, visionComponents);
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with vision_id: {}", vision.getId());
            return RepresentationBuilder.buildVisionRepresentation(vision, null);
        } catch (VisionNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        }
    }

    public VisionComponentRep addVisionComponent(VisionRequest request) throws IOException, DBServiceException {
        VisionComponent visionComponent = visionBuz.addComponent(Long.valueOf(request.getVisionId()));
        return RepresentationBuilder.buildVisionComponentRep(visionComponent);
    }

    public VisionComponentRep updateVisionComponent(VisionRequest request) throws DBServiceException, IOException, VisionComponentNotFoundException {
        VisionComponent visionComponent = visionBuz.getComponentById(Long.valueOf(request.getComponentId()));
        if(request.getFiles() != null) {
            visionComponent.setFiles(request.getFiles());
        }
        if(request.getSummary() != null) {
            visionComponent.setSummary(request.getSummary());
        }
        if(request.getColor() != null) {
            visionComponent.setColor(request.getColor());
        }
        if(request.getDescription() != null) {
            visionComponent.setDescription(request.getDescription());
        }
        VisionComponent newComponent = visionBuz.updateComponent(visionComponent);
        return RepresentationBuilder.buildVisionComponentRep(newComponent);
    }

    public ListVisionComponentRep getVisionComponent(VisionRequest request) throws DBServiceException, IOException {
        ListVisionComponentRep visionComponentReps = new ListVisionComponentRep();
        try {
            if (request.getComponentId() != null) {
                VisionComponent visionComponent = visionBuz.getComponentById(Long.valueOf(request.getComponentId()));
                visionComponentReps.add(RepresentationBuilder.buildVisionComponentRep(visionComponent));
            } else {
                List<VisionComponent> visionComponents = visionBuz.getComponentByVisionId(Long.valueOf(request.getVisionId()));
                for (VisionComponent visionComponent : visionComponents) {
                    visionComponentReps.add(RepresentationBuilder.buildVisionComponentRep(visionComponent));
                }
            }
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with request: {}", GsonUtils.convertToString(request));
        }
        return visionComponentReps;
    }
}
