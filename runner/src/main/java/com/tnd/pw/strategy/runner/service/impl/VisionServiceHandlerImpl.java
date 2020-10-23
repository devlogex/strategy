package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.strategy.common.constants.LayoutType;
import com.tnd.pw.strategy.common.constants.ReportAction;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.service.VisionServiceHandler;
import com.tnd.pw.strategy.vision.entity.Vision;
import com.tnd.pw.strategy.vision.entity.VisionComponent;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import com.tnd.pw.strategy.vision.service.VisionComponentService;
import com.tnd.pw.strategy.vision.service.VisionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class VisionServiceHandlerImpl implements VisionServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisionServiceHandlerImpl.class);
    @Autowired
    private VisionService visionService;
    @Autowired
    private VisionComponentService visionComponentService;
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private SdkService sdkService;

    public VisionRepresentation addVision(StrategyRequest request) throws DBServiceException {
        Vision vision = visionService.create(request.getId());
        List<VisionComponent> visionComponents = createComponentDefaults(vision.getId());
        Layout layout = createLayout(vision.getId(), visionComponents);
        return RepresentationBuilder.buildVisionRepresentation(vision, visionComponents, layout, null);
    }

    private Layout createLayout(Long visionId, List<VisionComponent> visionComponents) throws DBServiceException {
        ArrayList<ArrayList<ArrayList<Long>>> layout = new ArrayList<>();
        for(int i = 0; i < visionComponents.size(); i++) {
            layout.add(new ArrayList<>());
            layout.get(i).add(new ArrayList<>());
            layout.get(i).get(0).add(visionComponents.get(i).getId());
        }
        return layoutService.create(visionId, LayoutType.VISION_COMPONENT.name(), GsonUtils.convertToString(layout));
    }

    public VisionRepresentation updateVision(StrategyRequest request) throws DBServiceException, VisionNotFoundException, ActionServiceFailedException {
        Vision vision = visionService.get(Vision.builder().id(request.getId()).build()).get(0);

        if(request.getFiles() != null) {
            vision.setFiles(request.getFiles());
        }
        if(request.getDescription() != null) {
            vision.setDescription(request.getDescription());
        }
        Vision newVision = visionService.update(vision);
        CsActionRepresentation actionRep = sdkService.getTodoComment(newVision.getId());
        List<VisionComponent> visionComponents;
        Layout layout;
        try {
            visionComponents = visionComponentService.getByVisionId(newVision.getId());
            layout = layoutService.get(vision.getId(), LayoutType.VISION_COMPONENT.name());
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with visionId: {}", newVision.getId());
            return RepresentationBuilder.buildVisionRepresentation(newVision, null, null, actionRep);
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] LayoutNotFoundException with visionId: {}", newVision.getId());
            return RepresentationBuilder.buildVisionRepresentation(newVision, null, null, actionRep);
        }
        return RepresentationBuilder.buildVisionRepresentation(newVision, visionComponents, layout, actionRep);
    }

    public VisionRepresentation getVision(StrategyRequest request) throws DBServiceException, ActionServiceFailedException {
        Vision vision = null;
        CsActionRepresentation actionRep = null;
        try {
            vision = visionService.get(
                    Vision.builder()
                            .id(request.getId())
                            .productId(request.getProductId())
                            .build()
            ).get(0);
            actionRep = sdkService.getTodoComment(vision.getId());
            List<VisionComponent> visionComponents = visionComponentService.getByVisionId(vision.getId());
            Layout layout = layoutService.get(vision.getId(), LayoutType.VISION_COMPONENT.name());
            return RepresentationBuilder.buildVisionRepresentation(vision, visionComponents, layout, actionRep);
        } catch (VisionNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionNotFoundException with id: {}", request.getId());
            return null;
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with vision_id: {}", request.getId());
            return RepresentationBuilder.buildVisionRepresentation(vision, null, null, actionRep);
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] LayoutNotFoundException with vision_id: {}", request.getId());
            return RepresentationBuilder.buildVisionRepresentation(vision, null, null, actionRep);
        }
    }

    public ListVisionComponentRep addVisionComponent(StrategyRequest request) throws DBServiceException, VisionNotFoundException, LayoutNotFoundException, VisionComponentNotFoundException {
        try {
            Vision vision = visionService.get(Vision.builder().id(request.getId()).build()).get(0);
            VisionComponent visionComponent = visionComponentService.create(request.getId(), request.getName(), request.getSummary(), request.getColor(), request.getDescription(), request.getFiles());
            Layout layout = layoutService.get(vision.getId(), LayoutType.VISION_COMPONENT.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutRep = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());

            if (layoutRep == null) {
                layoutRep = new ArrayList<>();
            }
            layoutRep.add(0,new ArrayList<>());
            layoutRep.get(0).add(new ArrayList<>());
            layoutRep.get(0).get(0).add(visionComponent.getId());

            layout.setLayout(GsonUtils.convertToString(layoutRep));
            layoutService.update(layout);
            List<VisionComponent> components = visionComponentService.getByVisionId(vision.getId());

            return RepresentationBuilder.buildListVisionComponentRep(components, layout);
        }
        catch (VisionNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionNotFoundException with id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] LayoutNotFoundException with vision_id: {}", request.getId());
            throw e;
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException after add component with vision_id: {}", request.getId());
            throw e;
        }
    }

    public VisionComponentRep updateVisionComponent(StrategyRequest request) throws DBServiceException, VisionComponentNotFoundException {
        VisionComponent visionComponent = visionComponentService.getById(request.getId());
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
        if(request.getName() != null) {
            visionComponent.setName(request.getName());
        }
        VisionComponent newComponent = visionComponentService.update(visionComponent);
        return RepresentationBuilder.buildVisionComponentRep(newComponent);
    }

    @Override
    public ListVisionComponentRep removeVisionComponent(StrategyRequest request) throws DBServiceException, VisionComponentNotFoundException, LayoutNotFoundException {
        Layout layout;
        VisionComponent component = null;
        try {
            component = visionComponentService.getById(request.getId());
            layout = layoutService.get(component.getVisionId(), LayoutType.VISION_COMPONENT.name());
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with component_id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] LayoutNotFoundException with parent_id: {}, type: {}", component.getVisionId(), LayoutType.VISION_COMPONENT.name());
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
        visionComponentService.remove(request.getId());
        List<VisionComponent> components = visionComponentService.getByVisionId(component.getVisionId());
        return RepresentationBuilder.buildListVisionComponentRep(components, layout);
    }

    @Override
    public ListVisionComponentRep getVisionComponentById(StrategyRequest request) throws DBServiceException {
        ListVisionComponentRep visionComponentReps = new ListVisionComponentRep();
        try {
            VisionComponent visionComponent = visionComponentService.getById(request.getId());
            visionComponentReps.add(RepresentationBuilder.buildVisionComponentRep(visionComponent));

        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with request: {}", GsonUtils.convertToString(request));
        }
        return visionComponentReps;
    }

    @Override
    public ListVisionComponentRep getVisionComponentByVisionId(StrategyRequest request) throws DBServiceException {
        ListVisionComponentRep visionComponentReps = new ListVisionComponentRep();
        try {
            List<VisionComponent> visionComponents = visionComponentService.getByVisionId(request.getId());
            Vision vision = visionService.get(Vision.builder().id(request.getId()).build()).get(0);
            Layout layout = layoutService.get(vision.getId(), LayoutType.VISION_COMPONENT.name());
            visionComponentReps = RepresentationBuilder.buildListVisionComponentRep(visionComponents, layout);
        } catch (VisionComponentNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionComponentNotFoundException with request: {}", GsonUtils.convertToString(request));
        } catch (VisionNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] VisionNotFoundException with request: {}", GsonUtils.convertToString(request));
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[VisionHandlerBuz] LayoutNotFoundException with request: {}", GsonUtils.convertToString(request));
        }
        return visionComponentReps;
    }

    private List<VisionComponent> createComponentDefaults(Long vision_id) throws DBServiceException {
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

    @Override
    public LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception {
        List<VisionComponent> visionComponents = visionComponentService.getByVisionId(parentId);
        Layout layout = layoutService.get(parentId, LayoutType.VISION_COMPONENT.name());
        return new LayoutRepresentation(RepresentationBuilder.buildListVisionComponentRep(visionComponents, layout));
    }
}
