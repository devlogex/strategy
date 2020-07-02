package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListPersonasRepresentation;
import com.tnd.pw.strategy.common.representations.PersonasRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.common.utils.GsonUtils;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.personas.exception.PersonasNotFoundException;
import com.tnd.pw.strategy.runner.service.PersonasServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class PersonasHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonasHandler.class);
    @Autowired
    private PersonasServiceHandler personasServiceHandler;

    @HandlerService(path = "/strategy/personas/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListPersonasRepresentation> addPersonas(BaseRequest<StrategyRequest> request) throws IOException, DBServiceException, PersonasNotFoundException {
        LOGGER.info("[PersonasHandler] addPersonas() - request: {}", GsonUtils.convertToString(request));
        ListPersonasRepresentation response = personasServiceHandler.addPersonas(request.getData());
        LOGGER.info("[PersonasHandler] addPersonas() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/personas/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<PersonasRepresentation> updatePersonas(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, PersonasNotFoundException {
        LOGGER.info("[PersonasHandler] updatePersonas() - request: {}", GsonUtils.convertToString(request));
        PersonasRepresentation response = personasServiceHandler.updatePersonas(request.getData());
        LOGGER.info("[PersonasHandler] updatePersonas() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/personas", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListPersonasRepresentation> getPersonas(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException {
        LOGGER.info("[PersonasHandler] getPersonas() - request: {}", GsonUtils.convertToString(request));
        ListPersonasRepresentation response = personasServiceHandler.getPersonas(request.getData());
        LOGGER.info("[PersonasHandler] getPersonas() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/personas/delete", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.StrategyRequest")
    public BaseResponse<ListPersonasRepresentation> removePersonas(BaseRequest<StrategyRequest> request) throws DBServiceException, IOException, LayoutNotFoundException, PersonasNotFoundException {
        LOGGER.info("[PersonasHandler] removePersonas() - request: {}", GsonUtils.convertToString(request));
        ListPersonasRepresentation response = personasServiceHandler.removePersonas(request.getData());
        LOGGER.info("[PersonasHandler] removePersonas() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
