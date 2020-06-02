package com.tnd.pw.strategy.runner.service;

import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.requests.StrategyRequest;

public interface LayoutServiceHandler {
    LayoutRepresentation updateLayout(StrategyRequest request) throws Exception;
}
