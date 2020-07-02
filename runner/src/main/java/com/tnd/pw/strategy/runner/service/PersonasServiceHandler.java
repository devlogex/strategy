package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListPersonasRepresentation;
import com.tnd.pw.strategy.common.representations.PersonasRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.personas.exception.PersonasNotFoundException;

import java.io.IOException;

public interface PersonasServiceHandler extends ServiceHandler{
    ListPersonasRepresentation addPersonas(StrategyRequest request) throws IOException, DBServiceException, PersonasNotFoundException;
    PersonasRepresentation updatePersonas(StrategyRequest request) throws DBServiceException, IOException, PersonasNotFoundException;
    ListPersonasRepresentation getPersonas(StrategyRequest request) throws DBServiceException, IOException, LayoutNotFoundException;
    ListPersonasRepresentation removePersonas(StrategyRequest request) throws IOException, DBServiceException, LayoutNotFoundException, PersonasNotFoundException;
}
