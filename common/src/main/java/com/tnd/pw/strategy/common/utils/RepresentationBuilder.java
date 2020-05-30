package com.tnd.pw.strategy.common.utils;

import com.google.common.reflect.TypeToken;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.ListVisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RepresentationBuilder {

    public static VisionRepresentation buildVisionRepresentation(Vision vision, List<VisionComponent> components, Layout layout) {
        VisionRepresentation visionRepresentation = new VisionRepresentation();
        visionRepresentation.setId(vision.getId());
        visionRepresentation.setWorkspaceId(vision.getWorkspaceId());
        visionRepresentation.setFiles(vision.getFiles());
        visionRepresentation.setDescription(vision.getDescription());
        if(components != null && layout != null) {
            List<VisionComponentRep> componentReps = new ArrayList<>();
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
            for(int i = 0; i < layoutEntity.size(); i++) {
                for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                    for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                        for(int x = 0; x < components.size(); x++) {
                            if(components.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                                componentReps.add(buildVisionComponentRep(components.get(x)));
                                break;
                            }
                        }
                    }
                }
            }
            visionRepresentation.setListComponent(componentReps);
        }
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

    public static ListVisionComponentRep buildListVisionComponentRep(List<VisionComponent> components, Layout layout) {
        ListVisionComponentRep visionComponentReps = new ListVisionComponentRep();
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < components.size(); x++) {
                        if(components.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            visionComponentReps.add(buildVisionComponentRep(components.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        return visionComponentReps;
    }
}
