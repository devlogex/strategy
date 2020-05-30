package com.tnd.pw.strategy.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.LayoutResponse;
import com.tnd.pw.strategy.common.requests.StrategyRequest;
import com.tnd.pw.strategy.layout.exception.LayoutNotFoundException;
import com.tnd.pw.strategy.runner.exception.InvalidDataRequestException;

import java.io.IOException;

public interface LayoutServiceHandler {
    LayoutResponse updateLayout(StrategyRequest request) throws Exception;
}
