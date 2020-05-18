package com.tnd.pw.strategy.common.utils;

import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;

import java.util.ArrayList;
import java.util.List;

public class RepresentationBuilder {
    public static VisionRepresentation buildVisionRepresentation(Vision vision, List<VisionComponent> listComponent) {
        VisionRepresentation visionRepresentation = new VisionRepresentation();
        visionRepresentation.setId(vision.getId());
        visionRepresentation.setWorkspaceId(vision.getWorkspaceId());
        visionRepresentation.setFiles(vision.getFiles());
        List<VisionComponentRep> list = new ArrayList<>();
        if(listComponent != null) {
            for (VisionComponent visionComponent : listComponent) {
                list.add(buildVisionComponentRep(visionComponent));
            }
        }
        visionRepresentation.setListComponent(list);
        return visionRepresentation;
    }

    public static VisionComponentRep buildVisionComponentRep(VisionComponent visionComponent) {
        VisionComponentRep visionComponentRep = new VisionComponentRep();
        visionComponentRep.setId(visionComponent.getId());
        visionComponentRep.setName(visionComponent.getName());
        visionComponentRep.setColor(visionComponent.getColor());
        visionComponentRep.setDescription(visionComponent.getDescription());
        visionComponentRep.setSummary(visionComponent.getSummary());
        visionComponentRep.setVisionId(visionComponent.getVisionId());
        return visionComponentRep;
    }
}
