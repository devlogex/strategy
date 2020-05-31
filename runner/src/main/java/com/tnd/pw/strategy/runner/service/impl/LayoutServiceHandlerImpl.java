package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.com.ioc.SpringApplicationContext;
import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.LayoutResponse;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.runner.exception.InvalidDataRequestException;
;
import com.tnd.pw.strategy.runner.service.LayoutServiceHandler;
import com.tnd.pw.strategy.runner.service.ServiceHandler;
import com.tnd.pw.strategy.runner.service.VisionServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;

public class LayoutServiceHandlerImpl implements LayoutServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LayoutServiceHandlerImpl.class);
    @Autowired
    private LayoutService layoutService;

    public LayoutResponse updateLayout(StrategyRequest request) throws Exception {
        Layout layout = layoutService.get(request.getWorkspaceId(), request.getLayoutType());
        ArrayList<ArrayList<ArrayList<Long>>> layoutRep = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        ArrayList<ArrayList<ArrayList<Long>>> newLayoutRep = GsonUtils.getGson().fromJson(request.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
        if(!checkLayout(layoutRep, newLayoutRep)) {
            LOGGER.error("[LayoutHandlerBuz] updateLayout() - Invalid data request \n layoutEntity: {} \n newLayout: {}", layoutRep, newLayoutRep);
            throw new InvalidDataRequestException("New layout not right !");
        }
        layout.setLayout(request.getLayout());
        layoutService.update(layout);

        ServiceHandler serviceHandler = getServiceHandler(request.getLayoutType());
        return serviceHandler.getLayoutInstance(request.getWorkspaceId(), request.getLayoutType());
    }

    private ServiceHandler getServiceHandler(String layoutType) {
        String visionComponent = LayoutType.VISION_COMPONENT.name();
        switch (layoutType) {
            case "VISION_COMPONENT":
                return SpringApplicationContext.getBean(VisionServiceHandler.class);
            default:
                throw new IllegalStateException("Unexpected value: " + layoutType);
        }
    }

    private boolean checkLayout(ArrayList<ArrayList<ArrayList<Long>>> layout, ArrayList<ArrayList<ArrayList<Long>>> newLayout) {
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
            for(int j = 0; j < newLayout.get(i).size(); j++) {
                for(int k = 0; k < newLayout.get(i).get(j).size(); k++) {
                    set.add(newLayout.get(i).get(j).get(k));
                }
            }
        }
        return size == set.size();
    }

}
