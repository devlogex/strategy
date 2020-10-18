package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.strategy.common.enums.GoalState;
import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.representations.FilterInfoRepresentation;
import com.tnd.pw.strategy.common.representations.GoalRepresentation;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.ListGoalRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.goal.entity.Goal;
import com.tnd.pw.strategy.goal.exception.GoalNotFoundException;
import com.tnd.pw.strategy.goal.service.GoalService;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.service.GoalServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoalServiceHandlerImpl implements GoalServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoalServiceHandlerImpl.class);
    @Autowired
    private GoalService goalServiceService;
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private SdkService sdkService;

    @Override
    public ListGoalRepresentation addGoal(StrategyRequest request) throws DBServiceException, GoalNotFoundException {
        Goal goal = goalServiceService.create(request.getId());
        Layout layout;
        try {
            layout = layoutService.get(request.getId(), LayoutType.GOAL.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).get(0).add(goal.getId());
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);
        } catch (LayoutNotFoundException e) {
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(0).add(new ArrayList<>());
            layoutEntity.get(0).get(0).add(goal.getId());
            layout = layoutService.create(request.getId(), LayoutType.GOAL.name(), GsonUtils.convertToString(layoutEntity));

        }
        List<Goal> goals = goalServiceService.get(Goal.builder().productId(request.getId()).build());
        return RepresentationBuilder.buildListGoalRepresentation(goals, layout);
    }

    @Override
    public GoalRepresentation updateGoal(StrategyRequest request) throws DBServiceException, GoalNotFoundException, ActionServiceFailedException {
        Goal goal = goalServiceService.get(Goal.builder().id(request.getId()).build()).get(0);
        if(request.getName() != null) {
            goal.setName(request.getName());
        }
        if(request.getDescription() != null) {
            goal.setDescription(request.getDescription());
        }
        if(request.getFiles() != null) {
            goal.setFiles(request.getFiles());
        }
        if(request.getParentGoal() != null) {
            goal.setParentGoal(request.getParentGoal());
        }
        if(request.getStatus() != null) {
            goal.setStatus(GoalState.valueOf(request.getStatus()).ordinal());
        }
        if(request.getTimeFrame() != null) {
            goal.setTimeFrame(request.getTimeFrame());
        }
        if(request.getColor() != null) {
            goal.setColor(request.getColor());
        }
        if(request.getMetric() != null) {
            goal.setMetric(request.getMetric());
        }
        if(request.getMetricDescription() != null) {
            goal.setMetricDescription(request.getMetricDescription());
        }
        if(request.getMetricFile() != null) {
            goal.setMetricFile(request.getMetricFile());
        }
        goalServiceService.update(goal);
        CsActionRepresentation actionRep = sdkService.getTodoComment(goal.getId());
        return RepresentationBuilder.buildGoalRepresentation(goal, actionRep);
    }

    @Override
    public ListGoalRepresentation getGoal(StrategyRequest request) throws DBServiceException, LayoutNotFoundException {
        try {
            List<Goal> goals = goalServiceService.get(
                    request.getStatus() == null ?
                        Goal.builder()
                                .id(request.getId())
                                .productId(request.getProductId())
                                .timeFrame(request.getTimeFrame()).build()
                            :
                            Goal.builder().id(request.getId())
                                    .productId(request.getProductId())
                                    .status(GoalState.valueOf(request.getStatus()).ordinal())
                                    .timeFrame(request.getTimeFrame()).build()
                    );
            Layout layout = layoutService.get(goals.get(0).getProductId(), LayoutType.GOAL.name());
            return RepresentationBuilder.buildListGoalRepresentation(goals, layout);
        } catch (GoalNotFoundException e) {
            LOGGER.error("[GoalServiceHandlerImpl] GoalNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[GoalServiceHandlerImpl] LayoutNotFoundException with request: {}", GsonUtils.convertToString(request));
            throw e;
        }
    }

    @Override
    public GoalRepresentation getGoalInfo(StrategyRequest request) throws DBServiceException, GoalNotFoundException, ActionServiceFailedException {
        Goal goal = goalServiceService.get(
                Goal.builder()
                        .id(request.getId())
                        .timeFrame(request.getTimeFrame()).build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(goal.getId());
        return RepresentationBuilder.buildGoalRepresentation(goal, actionRep);
    }

    @Override
    public ListGoalRepresentation removeGoal(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, GoalNotFoundException {
        Layout layout;
        Goal goal = null;
        try {
            goal = goalServiceService.get(Goal.builder().id(request.getId()).build()).get(0);
            layout = layoutService.get(goal.getProductId(), LayoutType.GOAL.name());
        } catch (GoalNotFoundException e) {
            LOGGER.error("[GoalServiceHandlerImpl] GoalNotFoundException with id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[GoalServiceHandlerImpl] LayoutNotFoundException with parent_id: {}, type: {}", goal.getProductId(), LayoutType.GOAL.name());
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
        goalServiceService.remove(goal.getId());

        List<Goal> goals = null;
        try {
            goals = goalServiceService.get(Goal.builder().productId(goal.getProductId()).build());
        } catch (GoalNotFoundException e) {
            return null;
        }
        return RepresentationBuilder.buildListGoalRepresentation(goals, layout);
    }

    @Override
    public FilterInfoRepresentation getFilterInfos(StrategyRequest request) throws DBServiceException {
        List<String> timeFrames = goalServiceService.getTimeFrames(request.getId());
        return new FilterInfoRepresentation(timeFrames, null);
    }

    @Override
    public LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception {
        List<Goal> goals = goalServiceService.get(Goal.builder().productId(parentId).build());
        Layout layout = layoutService.get(parentId, layoutType);
        return new LayoutRepresentation(RepresentationBuilder.buildListGoalRepresentation(goals, layout));
    }
}
