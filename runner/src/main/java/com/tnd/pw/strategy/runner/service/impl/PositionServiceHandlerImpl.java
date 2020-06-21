package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.positioning.entity.Position;
import com.tnd.pw.strategy.positioning.entity.PositionComponent;
import com.tnd.pw.strategy.positioning.exception.PositionComponentNotFoundException;
import com.tnd.pw.strategy.positioning.exception.PositionNotFoundException;
import com.tnd.pw.strategy.positioning.service.PositionComponentService;
import com.tnd.pw.strategy.positioning.service.PositionService;
import com.tnd.pw.strategy.runner.service.PositionServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PositionServiceHandlerImpl implements PositionServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionServiceHandlerImpl.class);
    @Autowired
    private PositionService positionService;
    @Autowired
    private PositionComponentService positionComponentService;
    @Autowired
    private LayoutService layoutService;

    @Override
    public ListPositionRepresentation addPosition(StrategyRequest request) throws IOException, DBServiceException, PositionNotFoundException {
        Position position = positionService.create(request.getWorkspaceId());
        Layout layout;
        try {
            layout = layoutService.get(request.getWorkspaceId(), LayoutType.POSITION.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).get(0).add(position.getId());
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);
        } catch (LayoutNotFoundException e) {
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(0).add(new ArrayList<>());
            layoutEntity.get(0).get(0).add(position.getId());
            layout = layoutService.create(request.getWorkspaceId(), LayoutType.POSITION.name(), GsonUtils.convertToString(layoutEntity));

        }
        LayoutRepresentation layoutRepresentation = createPositionComponentDefaults(position);
        List<Position> positions = positionService.get(null, request.getWorkspaceId(), null, null);
        return RepresentationBuilder.buildListPositionRepresentation(positions, layout, layoutRepresentation.getLayout());
    }

    @Override
    public PositionRepresentation updatePosition(StrategyRequest request) throws DBServiceException, IOException, PositionNotFoundException {
        Position position = positionService.get(request.getPositionId(), null, null, null).get(0);
        if(request.getPositionName() != null) {
            position.setName(request.getPositionName());
        }
        if(request.getFiles() != null) {
            position.setFiles(request.getFiles());
        }
        if(request.getDescription() != null) {
            position.setDescription(request.getDescription());
        }
        if(request.getTimeFrame() != null) {
            position.setTimeFrame(request.getTimeFrame());
        }
        if(request.getBuzType() != null) {
            position.setBuzType(request.getBuzType());
        }
        positionService.update(position);
        return RepresentationBuilder.buildPositionRepresentation(position);
    }

    @Override
    public ListPositionRepresentation getPosition(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        try {
            List<Position> positions = positionService.get(
                    request.getPositionId(),
                    request.getWorkspaceId(),
                    request.getBuzType(),
                    request.getTimeFrame());
            Layout layout = layoutService.get(positions.get(0).getWorkspaceId(), LayoutType.POSITION.name());
            return RepresentationBuilder.buildListPositionRepresentation(positions, layout, null);
        } catch (PositionNotFoundException e) {
            LOGGER.error("[PositionServiceHandlerImpl] PositionNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[PositionServiceHandlerImpl] LayoutNotFoundException with request: {}", GsonUtils.convertToString(request));
            throw e;
        }
    }

    @Override
    public ListPositionRepresentation removePosition(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionNotFoundException {
        Layout layout;
        Position position = null;
        try {
            position = positionService.get(request.getPositionId(),null,null,null).get(0);
            layout = layoutService.get(position.getWorkspaceId(), LayoutType.POSITION.name());
        } catch (PositionNotFoundException e) {
            LOGGER.error("[PositionServiceHandlerImpl] PositionNotFoundException with id: {}", request.getPositionId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[PositionServiceHandlerImpl] LayoutNotFoundException with parent_id: {}, type: {}", position.getWorkspaceId(), LayoutType.POSITION.name());
            throw e;
        }

        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        outerLoop:
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    if(layoutEntity.get(i).get(j).get(k).compareTo(request.getPositionId()) == 0) {
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
        positionService.remove(position.getId());

        positionComponentService.remove(null , position.getId());
        layoutService.remove(position.getId(), LayoutType.POSITION_COMPONENT.name());
        List<Position> positions = null;
        try {
            positions = positionService.get(null, position.getWorkspaceId(), null, null);
        } catch (PositionNotFoundException e) {
            return null;
        }
        return RepresentationBuilder.buildListPositionRepresentation(positions, layout, null);
    }

    @Override
    public LayoutRepresentation addPositionComponent(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException {
        try {
            PositionComponent component = positionComponentService.create(request.getPositionId(), request.getName(), request.getColor(), request.getDescription(), request.getFiles());
            Layout layout = layoutService.get(component.getPositionId(), LayoutType.POSITION_COMPONENT.name());
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
            List<PositionComponent> components = positionComponentService.get(null, component.getPositionId());
            return RepresentationBuilder.buildListPositionComponentRep(layoutEntity, components);
        } catch (PositionComponentNotFoundException e) {
            LOGGER.error("[PositionServiceHandlerImpl] PositionComponentNotFoundException with component_id: {}", request.getComponentId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[PositionServiceHandlerImpl] LayoutNotFoundException with component_id: {}", request.getComponentId());
            throw e;
        }
    }

    @Override
    public PositionComponentRep updatePositionComponent(StrategyRequest request) throws DBServiceException, IOException, PositionComponentNotFoundException {
        PositionComponent positionComponent = positionComponentService.get(request.getComponentId(), null).get(0);
        if(request.getColor() != null) {
            positionComponent.setColor(request.getColor());
        }
        if(request.getName() != null) {
            positionComponent.setName(request.getName());
        }
        if(request.getDescription() != null) {
            positionComponent.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            positionComponent.setFiles(request.getFiles());
        }
        positionComponentService.update(positionComponent);
        return RepresentationBuilder.buildPositionComponentRep(positionComponent);
    }

    @Override
    public LayoutRepresentation getPositionComponent(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        try{
            if(request.getComponentId() != null) {
                PositionComponent component = positionComponentService.get(request.getComponentId(), null).get(0);
                return new LayoutRepresentation(component);
            }
            else {
                List<PositionComponent> components = positionComponentService.get(null, request.getPositionId());
                Layout layout = layoutService.get(request.getPositionId(), LayoutType.POSITION_COMPONENT.name());
                return RepresentationBuilder.buildListPositionComponentRep(layout, components);
            }
        } catch (PositionComponentNotFoundException e) {
            LOGGER.error("[PositionServiceHandlerImpl] PositionComponentNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        }
    }

    @Override
    public LayoutRepresentation removePositionComponent(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PositionComponentNotFoundException {
        Layout layout;
        PositionComponent component = null;
        try {
            component = positionComponentService.get(request.getComponentId(), null).get(0);
            layout = layoutService.get(component.getPositionId(), LayoutType.POSITION_COMPONENT.name());
        } catch (PositionComponentNotFoundException e) {
            LOGGER.error("[PositionHandlerBuz] PositionComponentNotFoundException with component_id: {}", request.getComponentId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[PositionHandlerBuz] LayoutNotFoundException with parent_id: {}, type: {}", component.getPositionId(), LayoutType.POSITION_COMPONENT.name());
            throw e;
        }

        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
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
        positionComponentService.remove(request.getComponentId(), null );
        List<PositionComponent> components = positionComponentService.get(null, component.getPositionId());
        return RepresentationBuilder.buildListPositionComponentRep(layout, components);
    }

    @Override
    public LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception {
        if(layoutType.equals(LayoutType.POSITION.name())) {
            List<Position> positions = positionService.get(null, parentId, null, null);
            Layout layout = layoutService.get(parentId, layoutType);
            return new LayoutRepresentation(RepresentationBuilder.buildListPositionRepresentation(positions, layout, null));
        }
        else {
            List<PositionComponent> components = positionComponentService.get(null, parentId);
            Layout layout = layoutService.get(parentId, layoutType);
            return new LayoutRepresentation(RepresentationBuilder.buildListPositionComponentRep(layout, components));
        }
    }

    private LayoutRepresentation createPositionComponentDefaults(Position position) throws IOException, DBServiceException {
        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<PositionComponentRep>>> layout = new ArrayList<>();
        layout.add(new ArrayList<>());
        layout.get(0).add(new ArrayList<>());
        layout.get(0).get(0).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Vision", "#F9931A", null, null)));

        layout.add(new ArrayList<>());
        layout.get(1).add(new ArrayList<>());
        layout.get(1).get(0).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Mission", "#509308", null, null)));

        layout.add(new ArrayList<>());
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(0).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Category", "#A5A5A5", null, null)));
        layout.get(2).add(new ArrayList<>());
        layout.get(2).get(1).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Tagline", "#B0926C", null, null)));

        layout.add(new ArrayList<>());
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(0).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Customer challenge #1", "#8C443E", null, null)));
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(1).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Customer challenge #2", "#BB5A53", null, null)));
        layout.get(3).add(new ArrayList<>());
        layout.get(3).get(2).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Customer challenge #3", "#EA7168", null, null)));

        layout.add(new ArrayList<>());
        layout.get(4).add(new ArrayList<>());
        layout.get(4).get(0).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Company differentiator #1", "#01457C", null, null)));
        layout.get(4).add(new ArrayList<>());
        layout.get(4).get(1).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Company differentiator #2", "#015CA6", null, null)));
        layout.get(4).add(new ArrayList<>());
        layout.get(4).get(2).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Company differentiator #3", "#0173CF", null, null)));

        layout.add(new ArrayList<>());
        layout.get(5).add(new ArrayList<>());
        layout.get(5).get(0).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Product differentiator #1", "#436A78", null, null)));
        layout.get(5).add(new ArrayList<>());
        layout.get(5).get(1).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Product differentiator #2", "#598DA0", null, null)));
        layout.get(5).add(new ArrayList<>());
        layout.get(5).get(2).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Product differentiator #3", "#6FB0C8", null, null)));

        layout.add(new ArrayList<>());
        layout.get(6).add(new ArrayList<>());
        layout.get(6).get(0).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Brand essence", "#CCAA02", null, null)));

        layout.add(new ArrayList<>());
        layout.get(7).add(new ArrayList<>());
        layout.get(7).get(0).add(RepresentationBuilder.buildPositionComponentRep(
                positionComponentService.create(position.getId(), "Boilerplate", "#666666", null, null)));

        for(int i = 0; i < layout.size(); i++) {
            layoutEntity.add(new ArrayList<>());
            for(int j = 0; j < layout.get(i).size(); j++) {
                layoutEntity.get(i).add(new ArrayList<>());
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    layoutEntity.get(i).get(j).add(layout.get(i).get(j).get(k).getId());
                }
            }
        }
        layoutService.create(position.getId(), LayoutType.POSITION_COMPONENT.name(), GsonUtils.convertToString(layoutEntity));
        return new LayoutRepresentation(layout);
    }

}
