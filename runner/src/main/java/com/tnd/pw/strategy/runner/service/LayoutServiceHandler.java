package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;

import java.io.IOException;

public interface LayoutServiceHandler {
    LayoutRepresentation updateLayout(StrategyRequest request) throws Exception;
    LayoutRepresentation updateLayoutList(StrategyRequest request) throws Exception;

    LayoutRepresentation getLayout(StrategyRequest request) throws IOException, DBServiceException;
}
