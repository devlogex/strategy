package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.enums.InitiativeState;
import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.representations.InitiativeRepresentation;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.ListInitiativeRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.initiative.entity.Initiative;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import com.tnd.pw.strategy.initiative.service.InitiativeService;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.runner.service.InitiativeServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitiativeServiceHandlerImpl implements InitiativeServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitiativeServiceHandlerImpl.class);
    @Autowired
    private InitiativeService initiativeService;
    @Autowired
    private LayoutService layoutService;

    @Override
    public ListInitiativeRepresentation addInitiative(StrategyRequest request) throws IOException, DBServiceException, InitiativeNotFoundException {
        Initiative initiative = initiativeService.create(request.getProductId());
        Layout layout;
        try {
            layout = layoutService.get(request.getProductId(), LayoutType.INITIATIVE.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).get(0).add(initiative.getId());
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);
        } catch (LayoutNotFoundException e) {
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(0).add(new ArrayList<>());
            layoutEntity.get(0).get(0).add(initiative.getId());
            layout = layoutService.create(request.getProductId(), LayoutType.INITIATIVE.name(), GsonUtils.convertToString(layoutEntity));

        }
        List<Initiative> initiatives = initiativeService.get(Initiative.builder().productId(request.getProductId()).build());
        return RepresentationBuilder.buildListInitiativeRepresentation(initiatives, layout);
    }

    @Override
    public InitiativeRepresentation updateInitiative(StrategyRequest request) throws DBServiceException, IOException, InitiativeNotFoundException {
        Initiative initiative = initiativeService.get(Initiative.builder().id(request.getInitiativeId()).build()).get(0);
        if(request.getName() != null) {
            initiative.setName(request.getName());
        }
        if(request.getDescription() != null) {
            initiative.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            initiative.setFiles(request.getFiles());
        }
        if(request.getParentInitiative() != null) {
            initiative.setParentInitiative(request.getParentInitiative());
        }
        if(request.getStatus() != null) {
            initiative.setStatus(InitiativeState.valueOf(request.getStatus()).ordinal());
        }
        if(request.getTimeFrame() != null) {
            initiative.setTimeFrame(request.getTimeFrame());
        }
        if(request.getColor() != null) {
            initiative.setColor(request.getColor());
        }
        if(request.getStartAt() != null) {
            initiative.setStartAt(request.getStartAt());
        }
        if(request.getEndAt() != null) {
            initiative.setEndAt(request.getEndAt());
        }
        if(request.getVisiable() != null) {
            initiative.setVisible(request.getVisiable());
        }
        initiativeService.update(initiative);
        return RepresentationBuilder.buildInitiativeRepresentation(initiative);
    }

    @Override
    public ListInitiativeRepresentation getInitiative(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException {
        try {
            List<Initiative> initiatives = initiativeService.get(
                    request.getStatus() == null ?
                            Initiative.builder().id(request.getInitiativeId())
                                    .productId(request.getProductId())
                                    .timeFrame(request.getTimeFrame()).build()
                            :
                            Initiative.builder().id(request.getInitiativeId())
                                    .productId(request.getProductId())
                                    .status(InitiativeState.valueOf(request.getStatus()).ordinal())
                                    .timeFrame(request.getTimeFrame()).build()
            );
            Layout layout = layoutService.get(initiatives.get(0).getProductId(), LayoutType.INITIATIVE.name());
            return RepresentationBuilder.buildListInitiativeRepresentation(initiatives, layout);
        } catch (InitiativeNotFoundException e) {
            LOGGER.error("[InitiativeServiceHandlerImpl] InitiativeNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[InitiativeServiceHandlerImpl] LayoutNotFoundException with request: {}", GsonUtils.convertToString(request));
            throw e;
        }
    }

    @Override
    public ListInitiativeRepresentation removeInitiative(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, InitiativeNotFoundException {
        Layout layout;
        Initiative initiative = null;
        try {
            initiative = initiativeService.get(Initiative.builder().id(request.getInitiativeId()).build()).get(0);
            layout = layoutService.get(initiative.getProductId(), LayoutType.INITIATIVE.name());
        } catch (InitiativeNotFoundException e) {
            LOGGER.error("[InitiativeServiceHandlerImpl] InitiativeNotFoundException with id: {}", request.getInitiativeId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[InitiativeServiceHandlerImpl] LayoutNotFoundException with parent_id: {}, type: {}", initiative.getProductId(), LayoutType.INITIATIVE.name());
            throw e;
        }

        ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        outerLoop:
        for(int i = 0; i < layoutEntity.size(); i++) {
            for(int j = 0; j < layoutEntity.get(i).size(); j++) {
                for(int k = 0; k < layoutEntity.get(i).get(j).size(); k++) {
                    if(layoutEntity.get(i).get(j).get(k).compareTo(request.getInitiativeId()) == 0) {
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
        initiativeService.remove(initiative.getId());

        List<Initiative> initiatives = null;
        try {
            initiatives = initiativeService.get(Initiative.builder().productId(initiative.getProductId()).build());
        } catch (InitiativeNotFoundException e) {
            return null;
        }
        return RepresentationBuilder.buildListInitiativeRepresentation(initiatives, layout);
    }

    @Override
    public LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception {
        List<Initiative> initiatives = initiativeService.get(Initiative.builder().productId(parentId).build());
        Layout layout = layoutService.get(parentId, layoutType);
        return new LayoutRepresentation(RepresentationBuilder.buildListInitiativeRepresentation(initiatives, layout));
    }
}
