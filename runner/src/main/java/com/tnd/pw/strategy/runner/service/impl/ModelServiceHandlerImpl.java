package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.enums.ModelType;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.model.entity.Model;
import com.tnd.pw.strategy.model.entity.ModelComponent;
import com.tnd.pw.strategy.model.exception.ModelComponentNotFoundException;
import com.tnd.pw.strategy.model.exception.ModelNotFoundException;
import com.tnd.pw.strategy.model.service.ModelComponentService;
import com.tnd.pw.strategy.model.service.ModelService;
import com.tnd.pw.strategy.runner.service.ModelServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModelServiceHandlerImpl implements ModelServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelServiceHandlerImpl.class);
    @Autowired
    private ModelService modelService;
    @Autowired
    private ModelComponentService modelComponentService;
    @Autowired
    private LayoutService layoutService;

    @Override
    public ListModelRepresentation addModel(StrategyRequest request) throws IOException, DBServiceException, ModelNotFoundException {
        Model model = modelService.create(request.getId(), ModelType.valueOf(request.getModelType()).ordinal());
        Layout layout;
        try {
            layout = layoutService.get(request.getId(), LayoutType.MODEL.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).get(0).add(model.getId());
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);
        } catch (LayoutNotFoundException e) {
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(0).add(new ArrayList<>());
            layoutEntity.get(0).get(0).add(model.getId());
            layout = layoutService.create(request.getId(), LayoutType.MODEL.name(), GsonUtils.convertToString(layoutEntity));

        }
        LayoutRepresentation layoutRepresentation = createModelComponentDefaults(model);
        List<Model> models = modelService.get(Model.builder().productId(request.getId()).build());
        return RepresentationBuilder.buildListModelRepresentation(models, layout, layoutRepresentation.getLayout());
    }

    @Override
    public ModelRepresentation updateModel(StrategyRequest request) throws DBServiceException, IOException, ModelNotFoundException {
        Model model = modelService.get(Model.builder().id(request.getId()).build()).get(0);
        if(request.getName() != null) {
            model.setName(request.getName());
        }
        if(request.getFiles() != null) {
            model.setFiles(request.getFiles());
        }
        if(request.getDescription() != null) {
            model.setDescription(request.getDescription());
        }
        if(request.getTimeFrame() != null) {
            model.setTimeFrame(request.getTimeFrame());
        }
        if(request.getBuzType() != null) {
            model.setBuzType(request.getBuzType());
        }
        modelService.update(model);
        return RepresentationBuilder.buildModelRepresentation(model);
    }

    @Override
    public ListModelRepresentation getModel(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        try {
            List<Model> models = modelService.get(
                    request.getModelType() == null ?
                        Model.builder()
                            .id(request.getId())
                            .productId(request.getProductId())
                            .timeFrame(request.getTimeFrame()).build()
                    :
                        Model.builder()
                                .id(request.getId())
                                .productId(request.getProductId())
                                .timeFrame(request.getTimeFrame())
                                .type(ModelType.valueOf(request.getModelType()).ordinal()).build()
            );
            Layout layout = layoutService.get(models.get(0).getProductId(), LayoutType.MODEL.name());
            return RepresentationBuilder.buildListModelRepresentation(models, layout, null);
        } catch (ModelNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] ModelNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] LayoutNotFoundException with request: {}", GsonUtils.convertToString(request));
            throw e;
        }
    }

    @Override
    public ListModelRepresentation removeModel(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, ModelNotFoundException {
        Layout layout;
        Model model = null;
        try {
            model = modelService.get(Model.builder().id(request.getId()).build()).get(0);
            layout = layoutService.get(model.getProductId(), LayoutType.MODEL.name());
        } catch (ModelNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] ModelNotFoundException with id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] LayoutNotFoundException with parent_id: {}, type: {}", model.getProductId(), LayoutType.MODEL.name());
            throw e;
        }

        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        outerLoop:
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    if(layoutEntity.get(i).get(j).get(k).compareTo(request.getId()) == 0) {
                        layoutEntity.get(i).get(j).remove(k);
                        if(layoutEntity.get(i).get(j).size() == 0) {
                            layoutEntity.get(i).remove(j);
                        }
                        if(layoutEntity.get(i).size() == 0) {
                            layoutEntity.remove(i);
                        }
                        break outerLoop;
                    }
                }
            }
        }
        layout.setLayout(GsonUtils.convertToString(layoutEntity));
        layoutService.update(layout);
        modelService.remove(model.getId());

        modelComponentService.remove(null , model.getId());
        layoutService.remove(model.getId(), LayoutType.MODEL_COMPONENT.name());
        List<Model> models = null;
        try {
            models = modelService.get(Model.builder().productId(model.getProductId()).build());
        } catch (ModelNotFoundException e) {
            return null;
        }
        return RepresentationBuilder.buildListModelRepresentation(models, layout, null);
    }

    @Override
    public LayoutRepresentation addModelComponent(StrategyRequest request) throws IOException, DBServiceException, ModelComponentNotFoundException, LayoutNotFoundException {
        try {
            ModelComponent component = modelComponentService.create(request.getId(), request.getName(), request.getColor(), request.getDescription(), request.getFiles());
            Layout layout = layoutService.get(component.getModelId(), LayoutType.MODEL_COMPONENT.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
            if (layoutEntity.get(0) != null) {
                layoutEntity.get(0).get(0).add(0, component.getId());
            } else {
                layoutEntity.add(new ArrayList<>());
                layoutEntity.get(0).add(new ArrayList<>());
                layoutEntity.get(0).get(0).add(component.getId());
            }
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);
            List<ModelComponent> components = modelComponentService.get(ModelComponent.builder().modelId(component.getModelId()).build());
            return RepresentationBuilder.buildListModelComponentRep(layoutEntity, components);
        } catch (ModelComponentNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] ModelComponentNotFoundException with component_id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] LayoutNotFoundException with component_id: {}", request.getId());
            throw e;
        }
    }

    @Override
    public ModelComponentRep updateModelComponent(StrategyRequest request) throws DBServiceException, IOException, ModelComponentNotFoundException {
        ModelComponent modelComponent = modelComponentService.get(ModelComponent.builder().id(request.getId()).build()).get(0);
        if(request.getColor() != null) {
            modelComponent.setColor(request.getColor());
        }
        if(request.getName() != null) {
            modelComponent.setName(request.getName());
        }
        if(request.getDescription() != null) {
            modelComponent.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            modelComponent.setFiles(request.getFiles());
        }
        modelComponentService.update(modelComponent);
        return RepresentationBuilder.buildModelComponentRep(modelComponent);
    }

    @Override
    public LayoutRepresentation removeModelComponent(StrategyRequest request) throws IOException, DBServiceException, ModelComponentNotFoundException, LayoutNotFoundException {
        Layout layout;
        ModelComponent component = null;
        try {
            component = modelComponentService.get(ModelComponent.builder().id(request.getId()).build()).get(0);
            layout = layoutService.get(component.getModelId(), LayoutType.MODEL_COMPONENT.name());
        } catch (ModelComponentNotFoundException e) {
            LOGGER.error("[ModelHandlerBuz] ModelComponentNotFoundException with component_id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[ModelHandlerBuz] LayoutNotFoundException with parent_id: {}, type: {}", component.getModelId(), LayoutType.MODEL_COMPONENT.name());
            throw e;
        }

        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        outerLoop:
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    if(layoutEntity.get(i).get(j).get(k).compareTo(request.getId()) == 0) {
                        layoutEntity.get(i).get(j).remove(k);
                        if(layoutEntity.get(i).get(j).size() == 0) {
                            layoutEntity.get(i).remove(j);
                        }
                        if(layoutEntity.get(i).size() == 0) {
                            layoutEntity.remove(i);
                        }
                        break outerLoop;
                    }
                }
            }
        }
        layout.setLayout(GsonUtils.convertToString(layoutEntity));
        layoutService.update(layout);
        modelComponentService.remove(request.getId(), null );
        List<ModelComponent> components = modelComponentService.get(ModelComponent.builder().modelId(component.getModelId()).build());
        return RepresentationBuilder.buildListModelComponentRep(layout, components);
    }

    @Override
    public FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws IOException, DBServiceException {
        List<String> timeFrames = modelService.getTimeFrames(request.getId());
        return new FilterInfoRepresentation(timeFrames, null);
    }

    @Override
    public LayoutRepresentation getModelComponentById(StrategyRequest request) throws IOException, DBServiceException {
        try{
            ModelComponent component = modelComponentService.get(ModelComponent.builder().id(request.getId()).build()).get(0);
            return new LayoutRepresentation(component);
        } catch (ModelComponentNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] ModelComponentNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        }
    }

    @Override
    public LayoutRepresentation getModelComponentByModelId(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException {
        try{
            List<ModelComponent> components = modelComponentService.get(ModelComponent.builder().modelId(request.getId()).build());
            Layout layout = layoutService.get(request.getId(), LayoutType.MODEL_COMPONENT.name());
            return RepresentationBuilder.buildListModelComponentRep(layout, components);
        } catch (ModelComponentNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] ModelComponentNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        }
    }

    @Override
    public LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception {
        if(layoutType.equals(LayoutType.MODEL.name())) {
            List<Model> models = modelService.get(Model.builder().productId(parentId).build());
            Layout layout = layoutService.get(parentId, layoutType);
            return new LayoutRepresentation(RepresentationBuilder.buildListModelRepresentation(models, layout, null));
        }
        else {
            List<ModelComponent> components = modelComponentService.get(ModelComponent.builder().modelId(parentId).build());
            Layout layout = layoutService.get(parentId, layoutType);
            return new LayoutRepresentation(RepresentationBuilder.buildListModelComponentRep(layout, components));
        }
    }

    private LayoutRepresentation createModelComponentDefaults(Model model) throws IOException, DBServiceException {
        switch (ModelType.values()[model.getType()]) {
            case MODEL_CANVAS:
                return createModelCanvasComponent(model);
            case LEAN_CANVAS:
                return createLeanCanvasComponent(model);
            case SWOT:
                return createSwotComponent(model);
            case PORTER:
                return createPorterComponent(model);
            case SEGMENT:
                return createSegmentComponent(model);
            case MARKETING_MATRIX:
                return createMarketingMatrixComponent(model);
            default:
                throw new IllegalStateException("Unexpected value: " + model.getType());
        }
    }

    private LayoutRepresentation createMarketingMatrixComponent(Model model) throws IOException, DBServiceException {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<ModelComponentRep>>> layout = new ArrayList<>();
        layout.add(new ArrayList<>());
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Product", "#025CA5", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Price", "#509409", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "People", "#6FB0C8", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Process", "#874B5D", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Promotion", "#F9921D", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Programs", "#E9644F", null, null)));
        layout.add(new ArrayList<>());
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Place", "#7F7F7F", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Physical environment", "#674C75", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Partners", "#B0916C", null, null)));
        layout.add(new ArrayList<>());
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Positioning", "#498587", null, null)));

        for(int i = 0; i < layout.size(); i++) {
            layoutEntity.add(new ArrayList<>());
            for(int j = 0; j < layout.get(i).size(); j++) {
                layoutEntity.get(i).add(new ArrayList<>());
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    layoutEntity.get(i).get(j).add(layout.get(i).get(j).get(k).getId());
                }
            }
        }
        layoutService.create(model.getId(), LayoutType.MODEL_COMPONENT.name(), GsonUtils.convertToString(layoutEntity));
        return new LayoutRepresentation(layout);
    }

    private LayoutRepresentation createSegmentComponent(Model model) throws IOException, DBServiceException {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<ModelComponentRep>>> layout = new ArrayList<>();
        layout.add(new ArrayList<>());
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Geographic", "#B0916C", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Demographic", "#4C9DDC", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Behavioral", "#674C75", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Psychographic", "#5A8DA0", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Size", "#7F7F7F", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Growth potential", "#509309", null, null)));
        layout.add(new ArrayList<>());
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Competitive activity", "#0373CF", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Risk", "#BB5952", null, null)));
        layout.add(new ArrayList<>());
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Approach", "#F9921D", null, null)));

        for(int i = 0; i < layout.size(); i++) {
            layoutEntity.add(new ArrayList<>());
            for(int j = 0; j < layout.get(i).size(); j++) {
                layoutEntity.get(i).add(new ArrayList<>());
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    layoutEntity.get(i).get(j).add(layout.get(i).get(j).get(k).getId());
                }
            }
        }
        layoutService.create(model.getId(), LayoutType.MODEL_COMPONENT.name(), GsonUtils.convertToString(layoutEntity));
        return new LayoutRepresentation(layout);
    }

    private LayoutRepresentation createPorterComponent(Model model) throws IOException, DBServiceException {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<ModelComponentRep>>> layout = new ArrayList<>();
        layout.add(new ArrayList<>());
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Buyer power", "#509309", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Alternative solutions", "#874B5D", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Existing competitors", "#AA0201", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "New rivals", "#874B5D", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(4).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Partner leverage", "#509309", null, null)));

        for(int i = 0; i < layout.size(); i++) {
            layoutEntity.add(new ArrayList<>());
            for(int j = 0; j < layout.get(i).size(); j++) {
                layoutEntity.get(i).add(new ArrayList<>());
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    layoutEntity.get(i).get(j).add(layout.get(i).get(j).get(k).getId());
                }
            }
        }
        layoutService.create(model.getId(), LayoutType.MODEL_COMPONENT.name(), GsonUtils.convertToString(layoutEntity));
        return new LayoutRepresentation(layout);
    }

    private LayoutRepresentation createSwotComponent(Model model) throws IOException, DBServiceException {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<ModelComponentRep>>> layout = new ArrayList<>();
        layout.add(new ArrayList<>());
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Strengths", "#0373CF", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Weaknesses", "#F9921D", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Opportunities", "#509309", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Threats", "#AA0201", null, null)));

        for(int i = 0; i < layout.size(); i++) {
            layoutEntity.add(new ArrayList<>());
            for(int j = 0; j < layout.get(i).size(); j++) {
                layoutEntity.get(i).add(new ArrayList<>());
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    layoutEntity.get(i).get(j).add(layout.get(i).get(j).get(k).getId());
                }
            }
        }
        layoutService.create(model.getId(), LayoutType.MODEL_COMPONENT.name(), GsonUtils.convertToString(layoutEntity));
        return new LayoutRepresentation(layout);
    }

    private LayoutRepresentation createLeanCanvasComponent(Model model) throws IOException, DBServiceException {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<ModelComponentRep>>> layout = new ArrayList<>();
        layout.add(new ArrayList<>());
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Problem", "#FA9201", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Solution", "#575757", null, null)));
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Key metrics", "#7F7F7F", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Unique value proposition", "#0373CF", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Unfair advantage", "#674C75", null, null)));
        layout.get(0).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Channels", "#95829E", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(4).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Customer segments", "#A0A493", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Cost structure", "#92A75C", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Revenue streams", "#509309", null, null)));

        for(int i = 0; i < layout.size(); i++) {
            layoutEntity.add(new ArrayList<>());
            for(int j = 0; j < layout.get(i).size(); j++) {
                layoutEntity.get(i).add(new ArrayList<>());
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    layoutEntity.get(i).get(j).add(layout.get(i).get(j).get(k).getId());
                }
            }
        }
        layoutService.create(model.getId(), LayoutType.MODEL_COMPONENT.name(), GsonUtils.convertToString(layoutEntity));
        return new LayoutRepresentation(layout);
    }

    private LayoutRepresentation createModelCanvasComponent(Model model) throws IOException, DBServiceException {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<ModelComponentRep>>> layout = new ArrayList<>();
        layout.add(new ArrayList<>());
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Description", "#666666", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Key objectives", "#8F9DA1", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Customer targets", "#80AEA7", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Customer challenges", "#80AEA7", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Our solution", "#1375CC", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Our value", "#F99426", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(4).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Our pricing", "#F99426", null, null)));
        layout.add(new ArrayList<>());
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Our messaging", "#A85D74", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Go-to-Market", "#664C73", null, null)));
        layout.add(new ArrayList<>());
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Investment required", "#B1C05C", null, null)));
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getId(), "Growth opportunity", "#67B624", null, null)));

        for(int i = 0; i < layout.size(); i++) {
            layoutEntity.add(new ArrayList<>());
            for(int j = 0; j < layout.get(i).size(); j++) {
                layoutEntity.get(i).add(new ArrayList<>());
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    layoutEntity.get(i).get(j).add(layout.get(i).get(j).get(k).getId());
                }
            }
        }
        layoutService.create(model.getId(), LayoutType.MODEL_COMPONENT.name(), GsonUtils.convertToString(layoutEntity));
        return new LayoutRepresentation(layout);
    }

}
