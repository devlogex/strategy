package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.com.ioc.SpringApplicationContext;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.runner.exception.InvalidDataRequestException;
;
import com.tnd.pw.strategy.runner.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class LayoutServiceHandlerImpl implements LayoutServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LayoutServiceHandlerImpl.class);
    @Autowired
    private LayoutService layoutService;

    public LayoutRepresentation updateLayout(StrategyRequest request) throws Exception {
        Layout layout = layoutService.get(request.getId(), request.getLayoutType());
        ArrayList<ArrayList<ArrayList<Long>>> layoutRep = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        ArrayList<ArrayList<ArrayList<Long>>> newLayoutRep = GsonUtils.getGson().fromJson(request.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        if(!checkLayout(layoutRep, newLayoutRep)) {
            LOGGER.error("[LayoutHandlerBuz] updateLayout() - Invalid data request \n layoutEntity: {} \n newLayout: {}", layoutRep, newLayoutRep);
            throw new InvalidDataRequestException("New layout not right !");
        }
        layout.setLayout(request.getLayout());
        layoutService.update(layout);

        ServiceHandler serviceHandler = getServiceHandler(request.getLayoutType());
        return serviceHandler.getLayoutInstance(request.getId(), request.getLayoutType());
    }

    @Override
    public LayoutRepresentation updateLayoutList(StrategyRequest request) throws Exception{
        Layout layout = layoutService.get(request.getId(), request.getLayoutType());
        ArrayList<ArrayList<ArrayList<Long>>> layoutRep = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        ArrayList<Long> newLayoutRep = GsonUtils.getGson().fromJson(request.getLayout(), new TypeToken<ArrayList<Long>>(){}.getType());
        if(!checkLayoutList(layoutRep, newLayoutRep)) {
            LOGGER.error("[LayoutHandlerBuz] updateLayout() - Invalid data request \n layoutEntity: {} \n newLayout: {}", layoutRep, newLayoutRep);
            throw new InvalidDataRequestException("New layout not right !");
        }
        for(int i = 0; i < newLayoutRep.size(); i++) {
            layoutRep.get(i).get(0).set(0, newLayoutRep.get(i));
        }
        layout.setLayout(GsonUtils.convertToString(layoutRep));
        layoutService.update(layout);
        ServiceHandler serviceHandler = getServiceHandler(request.getLayoutType());
        return serviceHandler.getLayoutInstance(request.getId(), request.getLayoutType());
    }

    @Override
    public LayoutRepresentation getLayout(StrategyRequest request) throws IOException, DBServiceException {
        try {
            Layout layout = layoutService.get(request.getId(), request.getLayoutType());
            return new LayoutRepresentation(layout);
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[LayoutHandlerBuz] getLayout() - LayoutNotFoundException, layout_parent: {}, layout_type: {}"
                    ,request.getId(),request.getLayoutType());
            return null;
        }
    }

    private ServiceHandler getServiceHandler(String layoutType) {
        LayoutType type = LayoutType.valueOf(layoutType);
        switch (type) {
            case VISION_COMPONENT:
                return SpringApplicationContext.getBean(VisionServiceHandler.class);
            case MODEL:case MODEL_COMPONENT:
                return SpringApplicationContext.getBean(ModelServiceHandler.class);
            case POSITION:case POSITION_COMPONENT:
                return SpringApplicationContext.getBean(PositionServiceHandler.class);
            case PERSONAS:
                return SpringApplicationContext.getBean(PersonasServiceHandler.class);
            case COMPETITOR:
                return SpringApplicationContext.getBean(CompetitorServiceHandler.class);
            case GOAL:
                return SpringApplicationContext.getBean(GoalServiceHandler.class);
            case INITIATIVE:
                return SpringApplicationContext.getBean(InitiativeServiceHandler.class);
            default:
                throw new IllegalStateException("Unexpected value: " + layoutType);
        }
    }

    private boolean checkLayout(ArrayList<ArrayList<ArrayList<Long>>> layout, ArrayList<ArrayList<ArrayList<Long>>> newLayout) {
        HashSet set = new HashSet();
        int s1 = 0, s2 = 0;
        for(int i = 0; i < layout.size(); i++) {
            for(int j = 0; j < layout.get(i).size(); j++) {
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    set.add(layout.get(i).get(j).get(k));
                    s1++;
                }
            }
        }
        int size = set.size();
        for(int i = 0; i < newLayout.size(); i++) {
            for(int j = 0; j < newLayout.get(i).size(); j++) {
                for(int k = 0; k < newLayout.get(i).get(j).size(); k++) {
                    set.add(newLayout.get(i).get(j).get(k));
                    s2++;
                }
            }
        }
        return size == set.size() && s1 == s2;
    }

    private boolean checkLayoutList(ArrayList<ArrayList<ArrayList<Long>>> layout, ArrayList<Long> newLayout) {
        HashSet set = new HashSet();
        for(int i = 0; i < layout.size(); i++) {
            for(int j = 0; j < layout.get(i).size(); j++) {
                for(int k = 0; k < layout.get(i).get(j).size(); k++) {
                    set.add(layout.get(i).get(j).get(k));
                }
            }
        }
        int size = set.size();
        for(int i = 0; i < newLayout.size(); i++) {
            set.add(newLayout.get(i));
        }
        return size == set.size() && size == newLayout.size();
    }
}
