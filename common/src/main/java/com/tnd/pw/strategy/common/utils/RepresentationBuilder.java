package com.tnd.pw.strategy.common.utils;

import com.google.common.reflect.TypeToken;
import com.tnd.pw.strategy.common.enums.GoalState;
import com.tnd.pw.strategy.common.enums.ModelType;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.competitor.entity.Competitor;
import com.tnd.pw.strategy.goal.entity.Goal;
import com.tnd.pw.strategy.initiative.entity.Initiative;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.model.entity.Model;
import com.tnd.pw.strategy.model.entity.ModelComponent;
import com.tnd.pw.strategy.personas.entity.Personas;
import com.tnd.pw.strategy.positioning.entity.Position;
import com.tnd.pw.strategy.positioning.entity.PositionComponent;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepresentationBuilder {

    public static VisionRepresentation buildVisionRepresentation(Vision vision, List<VisionComponent> components, Layout layout) {
        VisionRepresentation visionRepresentation = new VisionRepresentation();
        visionRepresentation.setId(vision.getId());
        visionRepresentation.setProductId(vision.getProductId());
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

//    public static <T> ArrayList<ArrayList<ArrayList<T>>> buildLayout(Layout layout, Class<T> clazz) {
//        return GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Class<T>>>>>(){}.getType());
//    }

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
        modelRepresentation.setProductId(model.getProductId());
        modelRepresentation.setType(ModelType.values()[model.getType()].name());
        modelRepresentation.setTimeFrame(model.getTimeFrame());
        modelRepresentation.setDescription(model.getDescription());
        modelRepresentation.setFiles(model.getFiles());
        modelRepresentation.setBuzType(model.getBuzType());
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

    public static LayoutRepresentation buildListModelComponentRep(Layout layout, List<ModelComponent> components) {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        return buildListModelComponentRep(layoutEntity, components);
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

    public static ListPositionRepresentation buildListPositionRepresentation(List<Position> positions, Layout layout, Object components) {
        ListPositionRepresentation positionRepresentations = new ListPositionRepresentation();
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < positions.size(); x++) {
                        if(positions.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            positionRepresentations.getListPosition().add(buildPositionRepresentation(positions.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        positionRepresentations.setListComponent(components);
        return positionRepresentations;
    }

    public static PositionRepresentation buildPositionRepresentation(Position position) {
        PositionRepresentation positionRepresentation = new PositionRepresentation();
        positionRepresentation.setId(position.getId());
        positionRepresentation.setName(position.getName());
        positionRepresentation.setTimeFrame(position.getTimeFrame());
        positionRepresentation.setBuzType(position.getBuzType());
        positionRepresentation.setDescription(position.getDescription());
        positionRepresentation.setFiles(position.getFiles());
        return positionRepresentation;
    }

    public static PositionComponentRep buildPositionComponentRep(PositionComponent positionComponent) {
        PositionComponentRep positionComponentRep = new PositionComponentRep();
        positionComponentRep.setId(positionComponent.getId());
        positionComponentRep.setName(positionComponent.getName());
        positionComponentRep.setFiles(positionComponent.getFiles());
        positionComponentRep.setColor(positionComponent.getColor());
        positionComponentRep.setDescription(positionComponent.getDescription());
        positionComponentRep.setPositionId(positionComponent.getPositionId());
        return positionComponentRep;
    }

    public static LayoutRepresentation buildListPositionComponentRep(Layout layout, List<PositionComponent> components) {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        return buildListPositionComponentRep(layoutEntity, components);
    }

    public static LayoutRepresentation buildListPositionComponentRep(ArrayList<ArrayList<ArrayList<Long>>> layoutEntity, List<PositionComponent> components) {
        ArrayList<ArrayList<ArrayList<PositionComponentRep>>> layout = new ArrayList<>();
        for(int i = 0; i < layoutEntity.size(); i++) {
            layout.add(new ArrayList<>());
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                layout.get(i).add(new ArrayList<>());
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < components.size(); x++) {
                        if(components.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            layout.get(i).get(j).add(buildPositionComponentRep(components.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        return new LayoutRepresentation(layout);
    }

    public static ListPersonasRepresentation buildListPersonasRepresentation(List<Personas> personases, Layout layout) {
        ListPersonasRepresentation list = new ListPersonasRepresentation();
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < personases.size(); x++) {
                        if(personases.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            list.add(buildPersonasRepresentation(personases.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static PersonasRepresentation buildPersonasRepresentation(Personas personas) {
        PersonasRepresentation personasRepresentation = new PersonasRepresentation();
        personasRepresentation.setId(personas.getId());
        personasRepresentation.setProductId(personas.getProductId());
        personasRepresentation.setName(personas.getName());
        personasRepresentation.setColor(personas.getColor());
        personasRepresentation.setImage(personas.getImage());
        HashMap<String, HashMap<String, String>> content = GsonUtils.getGson().fromJson(personas.getContent(), new TypeToken<HashMap<String, HashMap<String,String>>>(){}.getType());
        personasRepresentation.setContent(content);
        return personasRepresentation;
    }

    public static ListCompetitorRepresentation buildListCompetitorRepresentation(List<Competitor> competitors, Layout layout) {
        ListCompetitorRepresentation list = new ListCompetitorRepresentation();
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < competitors.size(); x++) {
                        if(competitors.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            list.add(buildCompetitorRepresentation(competitors.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static CompetitorRepresentation buildCompetitorRepresentation(Competitor competitor) {
        CompetitorRepresentation competitorRepresentation = new CompetitorRepresentation();
        competitorRepresentation.setId(competitor.getId());
        competitorRepresentation.setProductId(competitor.getProductId());
        competitorRepresentation.setName(competitor.getName());
        competitorRepresentation.setColor(competitor.getColor());
        competitorRepresentation.setImage(competitor.getImage());
        HashMap<String, HashMap<String, String>> content = GsonUtils.getGson().fromJson(competitor.getContent(), new TypeToken<HashMap<String, HashMap<String,String>>>(){}.getType());
        competitorRepresentation.setContent(content);
        competitorRepresentation.setUrl(competitor.getUrl());
        HashMap<String, Integer> score = GsonUtils.getGson().fromJson(competitor.getScore(), new TypeToken<HashMap<String, Integer>>(){}.getType());
        competitorRepresentation.setScore(score);
        return competitorRepresentation;
    }

    public static ListGoalRepresentation buildListGoalRepresentation(List<Goal> goals, Layout layout) {
        ListGoalRepresentation list = new ListGoalRepresentation();
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < goals.size(); x++) {
                        if(goals.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            list.add(buildGoalRepresentation(goals.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static GoalRepresentation buildGoalRepresentation(Goal goal) {
        GoalRepresentation goalRepresentation = new GoalRepresentation();
        goalRepresentation.setId(goal.getId());
        goalRepresentation.setProductId(goal.getProductId());
        goalRepresentation.setName(goal.getName());
        goalRepresentation.setDescription(goal.getDescription());
        goalRepresentation.setFiles(goal.getFiles());
        goalRepresentation.setParentGoal(goal.getParentGoal());
        goalRepresentation.setStatus(GoalState.values()[goal.getStatus()].name());
        goalRepresentation.setTimeFrame(goal.getTimeFrame());
        goalRepresentation.setColor(goal.getColor());
        goalRepresentation.setMetric(goal.getMetric());
        goalRepresentation.setMetricDescription(goal.getMetricDescription());
        goalRepresentation.setMetricFile(goal.getMetricFile());
        return goalRepresentation;
    }

    public static ListInitiativeRepresentation buildListInitiativeRepresentation(List<Initiative> initiatives, Layout layout) {
        ListInitiativeRepresentation list = new ListInitiativeRepresentation();
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    for(int x = 0; x < initiatives.size(); x++) {
                        if(initiatives.get(x).getId().compareTo(layoutEntity.get(i).get(j).get(k)) == 0) {
                            list.add(buildInitiativeRepresentation(initiatives.get(x)));
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static InitiativeRepresentation buildInitiativeRepresentation(Initiative initiative) {
        InitiativeRepresentation initiativeRepresentation = new InitiativeRepresentation();
        initiativeRepresentation.setId(initiative.getId());
        initiativeRepresentation.setProductId(initiative.getProductId());
        initiativeRepresentation.setName(initiative.getName());
        initiativeRepresentation.setDescription(initiative.getDescription());
        initiativeRepresentation.setFiles(initiative.getFiles());
        initiativeRepresentation.setParentInitiative(initiative.getParentInitiative());
        initiativeRepresentation.setStatus(GoalState.values()[initiative.getStatus()].name());
        initiativeRepresentation.setTimeFrame(initiative.getTimeFrame());
        initiativeRepresentation.setColor(initiative.getColor());
        initiativeRepresentation.setStartAt(initiative.getStartAt());
        initiativeRepresentation.setEndAt(initiative.getEndAt());
        initiativeRepresentation.setVisible(initiative.getVisible());
        return initiativeRepresentation;
    }
}
