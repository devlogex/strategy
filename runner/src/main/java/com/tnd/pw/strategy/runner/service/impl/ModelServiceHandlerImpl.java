package com.tnd.pw.strategy.runner.service.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.enums.ModelType;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.ListModelRepresentation;
import com.tnd.pw.strategy.common.representations.ModelComponentRep;
import com.tnd.pw.strategy.common.representations.ModelRepresentation;
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
        Model model = modelService.create(request.getWorkspaceId(), ModelType.valueOf(request.getModelType()).ordinal());
        Layout layout;
        try {
            layout = layoutService.get(request.getWorkspaceId(), LayoutType.MODEL.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = RepresentationBuilder.buildLayout(layout, Long.class);
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).get(0).add(request.getWorkspaceId());
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);
        } catch (LayoutNotFoundException e) {
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(0).add(new ArrayList<>());
            layoutEntity.get(0).get(0).add(request.getWorkspaceId());
            layout = layoutService.create(request.getWorkspaceId(), LayoutType.MODEL.name(), GsonUtils.convertToString(layoutEntity));

        }
        LayoutRepresentation layoutRepresentation = createModelComponentDefaults(model);
        List<Model> models = modelService.get(null, request.getWorkspaceId(), null, null);
        return RepresentationBuilder.buildListModelRepresentation(models, layout, layoutRepresentation.getLayout());
    }

    @Override
    public ModelRepresentation updateModel(StrategyRequest request) throws DBServiceException, IOException, ModelNotFoundException {
        Model model = modelService.get(request.getModelId(), null, null, null).get(0);
        model.setName(request.getModelName());
        model.setFiles(request.getFiles());
        model.setDescription(request.getDescription());
        model.setTimeFrame(request.getTimeFrame());
        modelService.update(model);
        return RepresentationBuilder.buildModelRepresentation(model);
    }

    @Override
    public ListModelRepresentation getModel(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        try {
            List<Model> models = modelService.get(request.getModelId(), request.getWorkspaceId(), ModelType.valueOf(request.getModelType()).ordinal(), request.getTimeFrame());
            Layout layout = layoutService.get(models.get(0).getWorkspaceId(), LayoutType.MODEL.name());
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
    public ListModelRepresentation removeModel(StrategyRequest request) throws IOException, DBServiceException, ModelNotFoundException, LayoutNotFoundException {
        Layout layout;
        Model model = null;
        try {
            model = modelService.get(request.getModelId(),null,null,null).get(0);
            layout = layoutService.get(model.getWorkspaceId(), LayoutType.MODEL.name());
        } catch (ModelNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] ModelNotFoundException with component_id: {}", request.getComponentId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] LayoutNotFoundException with parent_id: {}, type: {}", model.getWorkspaceId(), LayoutType.MODEL.name());
            throw e;
        }

        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = RepresentationBuilder.buildLayout(layout, Long.class);
        outerLoop:
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    if(layoutEntity.get(i).get(j).get(k).compareTo(request.getComponentId()) == 0) {
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
        List<Model> models = modelService.get(null, model.getWorkspaceId(), null, null);
        return RepresentationBuilder.buildListModelRepresentation(models, layout, null);
    }

    @Override
    public LayoutRepresentation addModelComponent(StrategyRequest request) throws IOException, DBServiceException, ModelComponentNotFoundException, LayoutNotFoundException {
        try {
            ModelComponent component = modelComponentService.create(request.getModelId(), request.getComponentName(), request.getColor(), request.getDescription(), request.getFiles());
            Layout layout = layoutService.get(component.getModelId(), LayoutType.MODEL_COMPONENT.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = RepresentationBuilder.buildLayout(layout, Long.class);
            if (layoutEntity.get(0) != null) {
                layoutEntity.get(0).get(0).add(0, component.getId());
            } else {
                layoutEntity.add(new ArrayList<>());
                layoutEntity.get(0).add(new ArrayList<>());
                layoutEntity.get(0).get(0).add(component.getId());
            }
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            List<ModelComponent> components = modelComponentService.get(null, component.getModelId());
            return RepresentationBuilder.buildListModelComponentRep(layoutEntity, components);
        } catch (ModelComponentNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] ModelComponentNotFoundException with component_id: {}", request.getComponentId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[ModelServiceHandlerImpl] LayoutNotFoundException with component_id: {}", request.getComponentId());
            throw e;
        }
    }

    @Override
    public ModelComponentRep updateModelComponent(StrategyRequest request) throws DBServiceException, IOException {
        return null;
    }

    @Override
    public LayoutRepresentation getModelComponent(StrategyRequest request) throws DBServiceException, IOException {
        return null;
    }

    @Override
    public LayoutRepresentation removeModelComponent(StrategyRequest request) throws IOException, DBServiceException {
        return null;
    }

    @Override
    public LayoutRepresentation getLayoutInstance(Long workspaceId, String layoutType) throws Exception {
        return null;
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
                modelComponentService.create(model.getWorkspaceId(), "Product", "#025CA5", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Price", "#509409", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "People", "#6FB0C8", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Process", "#874B5D", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Promotion", "#F9921D", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Programs", "#E9644F", null, null)));
        layout.add(new ArrayList<>());
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Place", "#7F7F7F", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Physical environment", "#674C75", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Partners", "#B0916C", null, null)));
        layout.add(new ArrayList<>());
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Positioning", "#498587", null, null)));

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
                modelComponentService.create(model.getWorkspaceId(), "Geographic", "#B0916C", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Demographic", "#4C9DDC", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Behavioral", "#674C75", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Psychographic", "#5A8DA0", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Size", "#7F7F7F", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Growth potential", "#509309", null, null)));
        layout.add(new ArrayList<>());
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Competitive activity", "#0373CF", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Risk", "#BB5952", null, null)));
        layout.add(new ArrayList<>());
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Approach", "#F9921D", null, null)));

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
                modelComponentService.create(model.getWorkspaceId(), "Buyer power", "#509309", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Alternative solutions", "#874B5D", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Existing competitors", "#AA0201", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "New rivals", "#874B5D", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(4).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Partner leverage", "#509309", null, null)));

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
                modelComponentService.create(model.getWorkspaceId(), "Strengths", "#0373CF", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Weaknesses", "#F9921D", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Opportunities", "#509309", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Threats", "#AA0201", null, null)));

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
                modelComponentService.create(model.getWorkspaceId(), "Problem", "#FA9201", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Solution", "#575757", null, null)));
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Key metrics", "#7F7F7F", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Unique value proposition", "#0373CF", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Unfair advantage", "#674C75", null, null)));
        layout.get(0).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Channels", "#95829E", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(4).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Customer segments", "#A0A493", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Cost structure", "#92A75C", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Revenue streams", "#509309", null, null)));

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
                modelComponentService.create(model.getWorkspaceId(), "Description", "#666666", null, null)));
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Key objectives", "#8F9DA1", null, null)));
        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Customer targets", "#80AEA7", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Customer challenges", "#80AEA7", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(2).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Our solution", "#1375CC", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(3).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Our value", "#F99426", null, null)));
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(4).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Our pricing", "#F99426", null, null)));
        layout.add(new ArrayList<>());
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Our messaging", "#A85D74", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Go-to-Market", "#664C73", null, null)));
        layout.add(new ArrayList<>());
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(0).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Investment required", "#B1C05C", null, null)));
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(1).add(RepresentationBuilder.buildModelComponentRep(
                modelComponentService.create(model.getWorkspaceId(), "Growth opportunity", "#67B624", null, null)));

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
