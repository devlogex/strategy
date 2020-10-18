package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListPersonasRepresentation;
import com.tnd.pw.strategy.common.representations.PersonasRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.personas.exception.PersonasNotFoundException;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;

import java.io.IOException;

public interface PersonasServiceHandler extends ServiceHandler{
    ListPersonasRepresentation addPersonas(StrategyRequest request) throws DBServiceException, PersonasNotFoundException;
    PersonasRepresentation updatePersonas(StrategyRequest request) throws DBServiceException, PersonasNotFoundException, ActionServiceFailedException;
    ListPersonasRepresentation getPersonas(StrategyRequest request) throws DBServiceException, LayoutNotFoundException;
    PersonasRepresentation getPersonasInfo(StrategyRequest request) throws DBServiceException, PersonasNotFoundException, ActionServiceFailedException;
    ListPersonasRepresentation removePersonas(StrategyRequest request) throws DBServiceException, LayoutNotFoundException, PersonasNotFoundException;
}
