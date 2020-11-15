package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.strategy.common.constants.LayoutType;
import com.tnd.pw.strategy.common.constants.ReportAction;
import com.tnd.pw.strategy.common.representations.CompetitorRepresentation;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.ListCompetitorRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.competitor.entity.Competitor;
import com.tnd.pw.strategy.competitor.exception.CompetitorNotFoundException;
import com.tnd.pw.strategy.competitor.service.CompetitorService;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.report.SendReportMes;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.service.CompetitorServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CompetitorServiceHandlerImpl implements CompetitorServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompetitorServiceHandlerImpl.class);
    @Autowired
    private CompetitorService competitorService;
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private SdkService sdkService;
    @Autowired
    private SendReportMes sendReportMes;

    @Override
    public ListCompetitorRepresentation addCompetitor(StrategyRequest request) throws DBServiceException, CompetitorNotFoundException {
        Competitor competitor = competitorService.create(request.getId());
        Layout layout;
        try {
            layout = layoutService.get(request.getId(), LayoutType.COMPETITOR.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).get(0).add(competitor.getId());
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);
        } catch (LayoutNotFoundException e) {
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(0).add(new ArrayList<>());
            layoutEntity.get(0).get(0).add(competitor.getId());
            layout = layoutService.create(request.getId(), LayoutType.COMPETITOR.name(), GsonUtils.convertToString(layoutEntity));

        }
        List<Competitor> competitors = competitorService.get(Competitor.builder().productId(request.getId()).build());
        sendReportMes.createHistory(request.getPayload().getUserId(), competitor.getId(), ReportAction.CREATE, GsonUtils.convertToString(competitor));
        return RepresentationBuilder.buildListCompetitorRepresentation(competitors, layout);
    }

    @Override
    public CompetitorRepresentation updateCompetitor(StrategyRequest request) throws DBServiceException, CompetitorNotFoundException, ActionServiceFailedException {
        Competitor competitor = competitorService.get(Competitor.builder().id(request.getId()).build()).get(0);
        String oldCompetitor = GsonUtils.convertToString(competitor);

        if(request.getName() != null) {
            competitor.setName(request.getName());
        }
        if(request.getImage() != null) {
            competitor.setImage(request.getImage());
        }
        if(request.getColor() != null) {
            competitor.setColor(request.getColor());
        }
        if(request.getContent() != null) {
            competitor.setContent(GsonUtils.convertToString(request.getContent()));
        }
        if(request.getScore() != null) {
            competitor.setScore(GsonUtils.convertToString(request.getScore()));
        }
        if(request.getUrl() != null) {
            competitor.setUrl(request.getUrl());
        }
        competitorService.update(competitor);
        CsActionRepresentation actionRep = sdkService.getTodoComment(competitor.getId());
        sendReportMes.createHistory(request.getPayload().getUserId(), competitor.getId(), ReportAction.UPDATE, oldCompetitor + "|" + GsonUtils.convertToString(competitor));
        return RepresentationBuilder.buildCompetitorRepresentation(competitor, actionRep);
    }

    @Override
    public ListCompetitorRepresentation getCompetitor(StrategyRequest request) throws DBServiceException, LayoutNotFoundException {
        try {
            List<Competitor> competitors = competitorService.get(
                    Competitor.builder()
                            .id(request.getId())
                            .productId(request.getProductId())
                            .build()
            );
            Layout layout = layoutService.get(competitors.get(0).getProductId(), LayoutType.COMPETITOR.name());
            return RepresentationBuilder.buildListCompetitorRepresentation(competitors, layout);
        } catch (CompetitorNotFoundException e) {
            LOGGER.error("[CompetitorServiceHandlerImpl] CompetitorNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[CompetitorServiceHandlerImpl] LayoutNotFoundException with request: {}", GsonUtils.convertToString(request));
            throw e;
        }
    }

    @Override
    public CompetitorRepresentation getCompetitorInfo(StrategyRequest request) throws DBServiceException, CompetitorNotFoundException, ActionServiceFailedException {
        Competitor competitor = competitorService.get(
                Competitor.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(competitor.getId());
        sendReportMes.createWatcher(request.getPayload().getUserId(), competitor.getId());
        return RepresentationBuilder.buildCompetitorRepresentation(competitor, actionRep);
    }

    @Override
    public ListCompetitorRepresentation removeCompetitor(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, CompetitorNotFoundException {
        Layout layout;
        Competitor competitor = null;
        try {
            competitor = competitorService.get(Competitor.builder().id(request.getId()).build()).get(0);
            layout = layoutService.get(competitor.getProductId(), LayoutType.COMPETITOR.name());
        } catch (CompetitorNotFoundException e) {
            LOGGER.error("[CompetitorServiceHandlerImpl] CompetitorNotFoundException with id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[CompetitorServiceHandlerImpl] LayoutNotFoundException with parent_id: {}, type: {}", competitor.getProductId(), LayoutType.COMPETITOR.name());
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
        competitorService.remove(competitor.getId());

        List<Competitor> competitors = null;
        try {
            competitors = competitorService.get(Competitor.builder().productId(competitor.getProductId()).build());
        } catch (CompetitorNotFoundException e) {
            return null;
        }
        return RepresentationBuilder.buildListCompetitorRepresentation(competitors, layout);
    }

    @Override
    public LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception {
        List<Competitor> competitors = competitorService.get(Competitor.builder().productId(parentId).build());
        Layout layout = layoutService.get(parentId, layoutType);
        return new LayoutRepresentation(RepresentationBuilder.buildListCompetitorRepresentation(competitors, layout));
    }
}
