package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.development.common.representations.FeatureRep;
import com.tnd.pw.development.common.representations.ReleaseRep;
import com.tnd.pw.strategy.common.constants.InitiativeState;
import com.tnd.pw.strategy.common.constants.LayoutType;
import com.tnd.pw.strategy.common.constants.ReportAction;
import com.tnd.pw.strategy.common.representations.*;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.goal.entity.Goal;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;
import com.tnd.pw.strategy.goal.service.GoalService;
import com.tnd.pw.strategy.initiative.entity.Initiative;
import com.tnd.pw.strategy.initiative.exception.InitiativeNotFoundException;
import com.tnd.pw.strategy.initiative.service.InitiativeService;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.report.SendReportMes;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.exception.DevServiceFailedException;
import com.tnd.pw.strategy.runner.exception.InvalidDataRequestException;
import com.tnd.pw.strategy.runner.service.InitiativeServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class InitiativeServiceHandlerImpl implements InitiativeServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitiativeServiceHandlerImpl.class);
    @Autowired
    private InitiativeService initiativeService;
    @Autowired
    private GoalService goalServiceService;
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private SdkService sdkService;
    @Autowired
    private SendReportMes sendReportMes;

    @Override
    public ListInitiativeRepresentation addInitiative(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException {
        Initiative initiative = initiativeService.create(
                Initiative.builder()
                        .productId(request.getId())
                        .status(request.getStatus() != null ? InitiativeState.valueOf(request.getStatus()).ordinal() : 0)
                        .build()
        );
        Layout layout;
        try {
            layout = layoutService.get(request.getId(), LayoutType.INITIATIVE.name());
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
            layout = layoutService.create(request.getId(), LayoutType.INITIATIVE.name(), GsonUtils.convertToString(layoutEntity));

        }

        Layout layoutState;
        try {
            layoutState = layoutService.get(request.getId(), LayoutType.INITIATIVE_STATE.name());
            HashMap<String, ArrayList<Long>> layoutEntity = GsonUtils.getGson().fromJson(layoutState.getLayout(), new TypeToken<HashMap<String, ArrayList<Long>>>(){}.getType());
            layoutEntity.get(InitiativeState.values()[initiative.getStatus()].name()).add(initiative.getId());
            layoutState.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layoutState);
        } catch (LayoutNotFoundException e) {
            HashMap<String, ArrayList<Long>> layoutEntity = new HashMap<>();
            for(InitiativeState state: InitiativeState.values()) {
                layoutEntity.put(state.name(), new ArrayList<>());
            }
            layoutEntity.get(InitiativeState.values()[initiative.getStatus()].name()).add(initiative.getId());
            layoutState = layoutService.create(request.getId(), LayoutType.INITIATIVE_STATE.name(), GsonUtils.convertToString(layoutEntity));
        }

        List<Initiative> initiatives = initiativeService.get(Initiative.builder().productId(request.getId()).build());

        sendReportMes.createHistory(request.getPayload().getUserId(), initiative.getId(), ReportAction.CREATE, GsonUtils.convertToString(initiative));
        if(request.getStatus() == null) {
            return RepresentationBuilder.buildListInitiativeRepresentation(initiatives, layout);
        }
        else {
            return RepresentationBuilder.buildListInitiativeStatusRep(initiatives, layoutState);
        }
    }

    @Override
    public InitiativeRep updateInitiative(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, LayoutNotFoundException, ActionServiceFailedException {
        Initiative initiative = initiativeService.get(Initiative.builder().id(request.getId()).build()).get(0);
        String oldInitiative = GsonUtils.convertToString(initiative);

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

        if(request.getGoals() != null) {
            initiative.setGoals(GsonUtils.convertToString(request.getGoals()));
        }

        if(request.getStatus() != null) {
            Layout layout = layoutService.get(initiative.getProductId(), LayoutType.INITIATIVE_STATE.name());
            HashMap<String, ArrayList<Long>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<HashMap<String, ArrayList<Long>>>(){}.getType());
            layoutEntity.get(InitiativeState.values()[initiative.getStatus()].name()).remove(initiative.getId());
            layoutEntity.get(InitiativeState.valueOf(request.getStatus()).name()).add(initiative.getId());
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);

            initiative.setStatus(InitiativeState.valueOf(request.getStatus()).ordinal());
        }
        initiativeService.update(initiative);
        sendReportMes.createHistory(request.getPayload().getUserId(), initiative.getId(), ReportAction.UPDATE, oldInitiative + "|" + GsonUtils.convertToString(initiative));
        return getInitiativeInfo(initiative);
    }

    @Override
    public ListInitiativeRepresentation getInitiative(StrategyRequest request) throws DBServiceException, LayoutNotFoundException {
        try {
            List<Initiative> initiatives = initiativeService.get(
                    request.getStatus() == null ?
                            Initiative.builder()
                                    .id(request.getId())
                                    .productId(request.getProductId())
                                    .timeFrame(request.getTimeFrame()).build()
                            :
                            Initiative.builder()
                                    .id(request.getId())
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
    public InitiativeRep getInitiativeInfo(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, ActionServiceFailedException {
        Initiative initiative = initiativeService.get(
                Initiative.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        sendReportMes.createWatcher(request.getPayload().getUserId(), initiative.getId());
        return getInitiativeInfo(initiative);
    }

    private InitiativeRep getInitiativeInfo(Initiative initiative) throws ActionServiceFailedException, DBServiceException {
        CsActionRepresentation actionRep = sdkService.getTodoComment(initiative.getId());
        List<Goal> goals = new ArrayList<>();
        try {
            List<Long> goalIds = GsonUtils.getGson().fromJson(
                    initiative.getGoals(),
                    new TypeToken<ArrayList<Long>>(){}.getType()
            );
            if(!CollectionUtils.isEmpty(goalIds)) {
                goals = goalServiceService.get(goalIds);
            }
        } catch (GoalNotFoundException e) {
        }
        return RepresentationBuilder.buildInitiativeRep(initiative, goals, actionRep);
    }

    @Override
    public ListInitiativeRepresentation removeInitiative(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, InitiativeNotFoundException {
        Layout layout;
        Layout layoutState;
        Initiative initiative = null;
        try {
            initiative = initiativeService.get(Initiative.builder().id(request.getId()).build()).get(0);
            layout = layoutService.get(initiative.getProductId(), LayoutType.INITIATIVE.name());
            layoutState = layoutService.get(initiative.getProductId(), LayoutType.INITIATIVE_STATE.name());
        } catch (InitiativeNotFoundException e) {
            LOGGER.error("[InitiativeServiceHandlerImpl] InitiativeNotFoundException with id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[InitiativeServiceHandlerImpl] LayoutNotFoundException with parent_id: {}, type: {} | {}", initiative.getProductId(), LayoutType.INITIATIVE.name(), LayoutType.INITIATIVE_STATE.name());
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

        HashMap<String, ArrayList<Long>> layoutEntityState = GsonUtils.getGson().fromJson(layoutState.getLayout(), new TypeToken<HashMap<String, ArrayList<Long>>>(){}.getType());
        outlook:
        for(String key: layoutEntityState.keySet()) {
            for(int i = 0; i < layoutEntityState.get(key).size(); i++) {
                if(layoutEntityState.get(key).get(i).compareTo(initiative.getId()) == 0) {
                    layoutEntityState.get(key).remove(i);
                    break outlook;
                }
            }
        }
        layoutState.setLayout(GsonUtils.convertToString(layoutEntityState));
        layoutService.update(layoutState);

        initiativeService.remove(initiative.getId());

        List<Initiative> initiatives = null;
        try {
            initiatives = initiativeService.get(Initiative.builder().productId(initiative.getProductId()).build());
        } catch (InitiativeNotFoundException e) {
            return null;
        }

        if(request.getStatus() == null) {
            return RepresentationBuilder.buildListInitiativeRepresentation(initiatives, layout);
        }
        else {
            return RepresentationBuilder.buildListInitiativeStatusRep(initiatives, layoutState);
        }
    }

    @Override
    public ListInitiativeRepresentation getInitiativeState(StrategyRequest request) throws DBServiceException, LayoutNotFoundException {
        try {
            List<Initiative> initiatives = initiativeService.get(
                    request.getStatus() == null ?
                            Initiative.builder()
                                    .id(request.getId())
                                    .productId(request.getProductId())
                                    .timeFrame(request.getTimeFrame()).build()
                            :
                            Initiative.builder()
                                    .id(request.getId())
                                    .productId(request.getProductId())
                                    .status(InitiativeState.valueOf(request.getStatus()).ordinal())
                                    .timeFrame(request.getTimeFrame()).build()
            );
            Layout layout = layoutService.get(initiatives.get(0).getProductId(), LayoutType.INITIATIVE_STATE.name());
            return RepresentationBuilder.buildListInitiativeStatusRep(initiatives, layout);
        } catch (InitiativeNotFoundException e) {
            LOGGER.error("[InitiativeServiceHandlerImpl] InitiativeNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[InitiativeServiceHandlerImpl] LayoutNotFoundException with request: {}", GsonUtils.convertToString(request));
            throw e;
        }
    }

    @Override
    public ListInitiativeRepresentation updateInitiativeState(StrategyRequest request) throws DBServiceException, InitiativeNotFoundException, LayoutNotFoundException, InvalidDataRequestException {
        Initiative initiative = initiativeService.get(Initiative.builder().id(request.getId()).build()).get(0);

        Layout layout = layoutService.get(initiative.getProductId(), LayoutType.INITIATIVE_STATE.name());
        HashMap<String, ArrayList<Long>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<HashMap<String, ArrayList<Long>>>(){}.getType());
        HashMap<String, ArrayList<Long>> newLayoutEntity = GsonUtils.getGson().fromJson(request.getLayout(), new TypeToken<HashMap<String, ArrayList<Long>>>(){}.getType());
        if(!checkLayout(layoutEntity, newLayoutEntity, initiative, InitiativeState.valueOf(request.getStatus()))) {
            LOGGER.error("[LayoutHandlerBuz] updateLayout() - Invalid data request \n layoutEntity: {} \n newLayout: {}", GsonUtils.convertToString(layoutEntity), request.getLayout());
            throw new InvalidDataRequestException("New layout not right !");
        }
        layout.setLayout(GsonUtils.convertToString(newLayoutEntity));
        layoutService.update(layout);

        initiative.setStatus(InitiativeState.valueOf(request.getStatus()).ordinal());
        initiativeService.update(initiative);

        List<Initiative> initiatives = initiativeService.get(Initiative.builder().productId(initiative.getProductId()).build());
        return RepresentationBuilder.buildListInitiativeStatusRep(initiatives, layout);
    }

    @Override
    public FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws DBServiceException {
        List<String> timeFrames = initiativeService.getTimeFrames(request.getId());
        return new FilterInfoRepresentation(timeFrames, null);
    }

    @Override
    public CsStrategyRep getInitiativeRoadmap(StrategyRequest request) throws DBServiceException, DevServiceFailedException {
        Long productId = request.getProductId();
        try {
            List<Initiative> initiatives = initiativeService.get(
                    Initiative.builder()
                            .productId(productId)
                            .build()
            );
            List<Long> initiativeIds = initiatives.stream()
                    .map(initiative -> initiative.getId())
                    .collect(Collectors.toList());
            List<ReleaseRep> releaseReps = sdkService.getReleases(productId).getReleaseReps();
            List<FeatureRep> featureReps = sdkService.getFeatures(initiativeIds).getFeatureReps();
            return RepresentationBuilder.buildInitiativeRoadmap(initiatives, releaseReps, featureReps);
        } catch (InitiativeNotFoundException e) {
            return new CsStrategyRep();
        }
    }

    @Override
    public LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception {
        List<Initiative> initiatives = initiativeService.get(Initiative.builder().productId(parentId).build());
        Layout layout = layoutService.get(parentId, layoutType);
        return new LayoutRepresentation(RepresentationBuilder.buildListInitiativeRepresentation(initiatives, layout));
    }

    private boolean checkLayout(HashMap<String, ArrayList<Long>> layout, HashMap<String, ArrayList<Long>> newLayout, Initiative obj, InitiativeState newState) {
        HashSet set = new HashSet();
        for(ArrayList<Long> list: layout.values()) {
            for(Long id: list) {
                set.add(id);
            }
        }
        int size = set.size();
        for(ArrayList<Long> list: newLayout.values()) {
            for(Long id: list) {
                set.add(id);
                if(id.compareTo(obj.getId()) == 0) {
                    for(String key: newLayout.keySet()) {
                        if(newLayout.get(key).contains(id)) {
                            if(!key.equals(newState.name())) {
                                return false;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return size == set.size();
    }
}
