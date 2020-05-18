package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListVisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.common.requests.VisionRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import com.tnd.pw.strategy.vision.service.VisionComponentService;
import com.tnd.pw.strategy.vision.service.VisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisionHandlerBuz {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisionHandlerBuz.class);
    @Autowired
    private VisionService visionService;
    @Autowired
    private VisionComponentService visionComponentService;

    private static int num_comp_default = 9;

    public VisionRepresentation addVision(VisionRequest request) throws IOException, DBServiceException {
        Vision vision = visionService.create(request.getWorkspaceId());
        List<VisionComponent> visionComponents = createComponentDefaults(vision.getId());
        return RepresentationBuilder.buildVisionRepresentation(vision, visionComponents);
    }

    public VisionRepresentation updateVision(VisionRequest request) throws DBServiceException, VisionNotFoundException, IOException {
        Vision vision = visionService.getById(request.getVisionId());
        if(request.getFiles() != null) {
            vision.setFiles(request.getFiles());
        }
        Vision newVision = visionService.update(vision);
        List<VisionComponent> visionComponents = null;
        try {
            visionComponents = visionComponentService.getByVisionId(newVision.getId());
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
                vision = visionService.getById(request.getVisionId());
            } else {
                vision = visionService.getByWorkspaceId(request.getWorkspaceId());
            }
            List<VisionComponent> visionComponents = visionComponentService.getByVisionId(vision.getId());
            return RepresentationBuilder.buildVisionRepresentation(vision, visionComponents);
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with vision_id: {}", request.getVisionId());
            return RepresentationBuilder.buildVisionRepresentation(vision, null);
        } catch (VisionNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionNotFoundException with id: {}", request.getVisionId());
            return null;
        }
    }

    public VisionComponentRep addVisionComponent(VisionRequest request) throws IOException, DBServiceException {
        VisionComponent visionComponent = visionComponentService.create(request.getVisionId(), request.getComponentName(), request.getSummary(), request.getColor(), request.getDescription(), request.getFiles());
        return RepresentationBuilder.buildVisionComponentRep(visionComponent);
    }

    public VisionComponentRep updateVisionComponent(VisionRequest request) throws DBServiceException, IOException, VisionComponentNotFoundException {
        VisionComponent visionComponent = visionComponentService.getById(request.getComponentId());
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
        if(request.getComponentName() != null) {
            visionComponent.setName(request.getComponentName());
        }
        VisionComponent newComponent = visionComponentService.update(visionComponent);
        return RepresentationBuilder.buildVisionComponentRep(newComponent);
    }

    public ListVisionComponentRep getVisionComponent(VisionRequest request) throws DBServiceException, IOException {
        ListVisionComponentRep visionComponentReps = new ListVisionComponentRep();
        try {
            if (request.getComponentId() != null) {
                VisionComponent visionComponent = visionComponentService.getById(request.getComponentId());
                visionComponentReps.add(RepresentationBuilder.buildVisionComponentRep(visionComponent));
            } else {
                List<VisionComponent> visionComponents = visionComponentService.getByVisionId(request.getVisionId());
                for (VisionComponent visionComponent : visionComponents) {
                    visionComponentReps.add(RepresentationBuilder.buildVisionComponentRep(visionComponent));
                }
            }
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with request: {}", GsonUtils.convertToString(request));
        }
        return visionComponentReps;
    }

    private List<VisionComponent> createComponentDefaults(Long vision_id) throws IOException, DBServiceException {
        List<VisionComponent> visionComponents = new ArrayList<>();
        visionComponents.add(visionComponentService.create(vision_id,"Opportunity","Summary...","#0173CF", "Description...",""));
        visionComponents.add(visionComponentService.create(vision_id,"Vision","Summary...","#674C75", "Description...",""));
        visionComponents.add(visionComponentService.create(vision_id,"Mission","Summary...","#E24D4D", "Description...",""));
        visionComponents.add(visionComponentService.create(vision_id,"Position","Summary...","#F9931A", "Description...",""));
        visionComponents.add(visionComponentService.create(vision_id,"Strengths","Summary...","#64B80A", "Description...",""));
        visionComponents.add(visionComponentService.create(vision_id,"Weaknesses","Summary...","#6FB0C8", "Description...",""));
        visionComponents.add(visionComponentService.create(vision_id,"Resources","Summary...","#D5B758", "Description...",""));
        visionComponents.add(visionComponentService.create(vision_id,"Threats","Summary...","#7F7F7F", "Description...",""));
        visionComponents.add(visionComponentService.create(vision_id,"Trends","Summary...","#A95C74", "Description...",""));

        return visionComponents;
    }
}
