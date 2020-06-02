package com.tnd.pw.strategy.common.utils;

import com.google.common.reflect.TypeToken;
import com.tnd.pw.strategy.common.enums.ModelType;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.model.entity.Model;
import com.tnd.pw.strategy.model.entity.ModelComponent;
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
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = buildLayout(layout, Long.class);
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

    public static <T> ArrayList<ArrayList<ArrayList<T>>> buildLayout(Layout layout, Class<T> clazz) {
        return GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<T>>>>(){}.getType());
    }

    public static ListModelRepresentation buildListModelRepresentation(List<Model> models, Layout layout, Object components) {
        ListModelRepresentation modelRepresentations = new ListModelRepresentation();
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < models.size(); x++) {
                        if(models.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            modelRepresentations.getListModel().add(buildModelRepresentation(models.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        modelRepresentations.setListComponent(components);
        return modelRepresentations;
    }

    public static ModelRepresentation buildModelRepresentation(Model model) {
        ModelRepresentation modelRepresentation = new ModelRepresentation();
        modelRepresentation.setId(model.getId());
        modelRepresentation.setName(model.getName());
        modelRepresentation.setType(ModelType.values()[model.getType()].name());
        modelRepresentation.setTimeFrame(model.getTimeFrame());
        modelRepresentation.setDescription(model.getDescription());
        modelRepresentation.setFiles(model.getFiles());
        return modelRepresentation;
    }

    public static ModelComponentRep buildModelComponentRep(ModelComponent modelComponent) {
        ModelComponentRep modelComponentRep = new ModelComponentRep();
        modelComponentRep.setId(modelComponent.getId());
        modelComponentRep.setName(modelComponent.getName());
        modelComponentRep.setFiles(modelComponent.getFiles());
        modelComponentRep.setColor(modelComponent.getColor());
        modelComponentRep.setDescription(modelComponent.getDescription());
        modelComponentRep.setModelId(modelComponent.getModelId());
        return modelComponentRep;
    }

    public static LayoutRepresentation buildListModelComponentRep(ArrayList<ArrayList<ArrayList<Long>>> layoutEntity, List<ModelComponent> components) {
        ArrayList<ArrayList<ArrayList<ModelComponentRep>>> layout = new ArrayList<>();
        for(int i = 0; i < layoutEntity.size(); i++) {
            layout.add(new ArrayList<>());
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                layout.get(i).add(new ArrayList<>());
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < components.size(); x++) {
                        if(components.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            layout.get(i).get(j).add(RepresentationBuilder.buildModelComponentRep(components.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        return new LayoutRepresentation(layout);
    }
}
