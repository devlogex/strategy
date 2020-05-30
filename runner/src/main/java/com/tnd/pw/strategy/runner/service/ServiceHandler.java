package com.tnd.pw.strategy.runner.service;

import com.tnd.pw.strategy.common.enums.LayoutType;
import com.tnd.pw.strategy.common.representations.LayoutRepresentation;
import com.tnd.pw.strategy.common.representations.LayoutResponse;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;

public interface ServiceHandler {
    LayoutResponse getLayoutInstance(Long workspaceId, String layoutType) throws Exception;
}
