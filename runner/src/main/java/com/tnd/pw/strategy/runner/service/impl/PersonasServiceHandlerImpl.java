package com.tnd.pw.strategy.runner.service.impl;

import com.google.common.reflect.TypeToken;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.report.common.constants.ReportAction;
import com.tnd.pw.strategy.common.constants.LayoutType;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.ListPersonasRepresentation;
import com.tnd.pw.strategy.common.representations.PersonasRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.common.utils.RepresentationBuilder;
import com.tnd.pw.strategy.layout.entity.Layout;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.layout.service.LayoutService;
import com.tnd.pw.strategy.personas.entity.Personas;
import com.tnd.pw.strategy.personas.exception.PersonasNotFoundException;
import com.tnd.pw.strategy.personas.service.PersonasService;
import com.tnd.pw.strategy.report.SendReportMes;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.service.PersonasServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class PersonasServiceHandlerImpl implements PersonasServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonasServiceHandlerImpl.class);
    @Autowired
    private PersonasService personasService;
    @Autowired
    private LayoutService layoutService;
    @Autowired
    private SdkService sdkService;
    @Autowired
    private SendReportMes sendReportMes;

    @Override
    public ListPersonasRepresentation addPersonas(StrategyRequest request) throws DBServiceException, PersonasNotFoundException {
        Personas personas = personasService.create(request.getId());
        Layout layout;
        try {
            layout = layoutService.get(request.getId(), LayoutType.PERSONAS.name());
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = GsonUtils.getGson().fromJson(layout.getLayout(), new TypeToken<ArrayList<ArrayList<ArrayList<Long>>>>(){}.getType());
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).add(new ArrayList<>());
            layoutEntity.get(layoutEntity.size() - 1).get(0).add(personas.getId());
            layout.setLayout(GsonUtils.convertToString(layoutEntity));
            layoutService.update(layout);
        } catch (LayoutNotFoundException e) {
            ArrayList<ArrayList<ArrayList<Long>>> layoutEntity = new ArrayList<>();
            layoutEntity.add(new ArrayList<>());
            layoutEntity.get(0).add(new ArrayList<>());
            layoutEntity.get(0).get(0).add(personas.getId());
            layout = layoutService.create(request.getId(), LayoutType.PERSONAS.name(), GsonUtils.convertToString(layoutEntity));

        }
        List<Personas> personases = personasService.get(Personas.builder().productId(request.getId()).build());
        sendReportMes.createHistory(request.getPayload().getUserId(), personas.getId(), ReportAction.CREATED, GsonUtils.convertToString(personas));
        return RepresentationBuilder.buildListPersonasRepresentation(personases, layout);
    }

    @Override
    public PersonasRepresentation updatePersonas(StrategyRequest request) throws DBServiceException, PersonasNotFoundException, ActionServiceFailedException {
        Personas personas = personasService.get(Personas.builder().id(request.getId()).build()).get(0);
        String oldPersonas = GsonUtils.convertToString(personas);
        if(request.getName() != null) {
            personas.setName(request.getName());
        }
        if(request.getImage() != null) {
            personas.setImage(request.getImage());
        }
        if(request.getColor() != null) {
            personas.setColor(request.getColor());
        }
        if(request.getContent() != null) {
            personas.setContent(GsonUtils.convertToString(request.getContent()));
        }
        personasService.update(personas);
        CsActionRepresentation actionRep = sdkService.getTodoComment(personas.getId());
        sendReportMes.createHistory(request.getPayload().getUserId(), personas.getId(), ReportAction.UPDATED, oldPersonas + "|" + GsonUtils.convertToString(personas));
        return RepresentationBuilder.buildPersonasRepresentation(personas, actionRep);
    }

    @Override
    public ListPersonasRepresentation getPersonas(StrategyRequest request) throws DBServiceException, LayoutNotFoundException {
        try {
            List<Personas> personasList = personasService.get(
                    Personas.builder()
                            .id(request.getId())
                            .productId(request.getProductId())
                            .build()
                    );
            Layout layout = layoutService.get(personasList.get(0).getProductId(), LayoutType.PERSONAS.name());
            return RepresentationBuilder.buildListPersonasRepresentation(personasList, layout);
        } catch (PersonasNotFoundException e) {
            LOGGER.error("[PersonasServiceHandlerImpl] PersonasNotFoundException with request: {}", GsonUtils.convertToString(request));
            return null;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[PersonasServiceHandlerImpl] LayoutNotFoundException with request: {}", GsonUtils.convertToString(request));
            throw e;
        }
    }

    @Override
    public PersonasRepresentation getPersonasInfo(StrategyRequest request) throws DBServiceException, PersonasNotFoundException, ActionServiceFailedException {
        Personas personas = personasService.get(
                Personas.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        CsActionRepresentation actionRep = sdkService.getTodoComment(personas.getId());
        sendReportMes.createWatcher(request.getPayload().getUserId(), personas.getId());
        return RepresentationBuilder.buildPersonasRepresentation(personas, actionRep);
    }

    @Override
    public ListPersonasRepresentation removePersonas(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, PersonasNotFoundException {
        Layout layout;
        Personas personas = null;
        try {
            personas = personasService.get(Personas.builder().id(request.getId()).build()).get(0);
            layout = layoutService.get(personas.getProductId(), LayoutType.PERSONAS.name());
        } catch (PersonasNotFoundException e) {
            LOGGER.error("[PersonasServiceHandlerImpl] PersonasNotFoundException with id: {}", request.getId());
            throw e;
        } catch (LayoutNotFoundException e) {
            LOGGER.error("[PersonasServiceHandlerImpl] LayoutNotFoundException with parent_id: {}, type: {}", personas.getProductId(), LayoutType.PERSONAS.name());
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
        personasService.remove(personas.getId());

        List<Personas> personasList = null;
        try {
            personasList = personasService.get(Personas.builder().productId(personas.getProductId()).build());
        } catch (PersonasNotFoundException e) {
            return null;
        }
        return RepresentationBuilder.buildListPersonasRepresentation(personasList, layout);
    }

    @Override
    public LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception {
        List<Personas> personasList = personasService.get(Personas.builder().productId(parentId).build());
        Layout layout = layoutService.get(parentId, layoutType);
        return new LayoutRepresentation(RepresentationBuilder.buildListPersonasRepresentation(personasList, layout));
    }
}
