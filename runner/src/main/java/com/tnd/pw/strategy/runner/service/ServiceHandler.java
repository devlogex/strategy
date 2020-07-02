package com.tnd.pw.strategy.runner.service;

import com.tnd.pw.strategy.common.representations.LayoutRepresentation;

public interface ServiceHandler {
    LayoutRepresentation getLayoutInstance(Long parentId, String layoutType) throws Exception;
}
