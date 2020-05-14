package com.tnd.pw.strategy.runner.handler;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.strategy.common.representations.ListVisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionComponentRep;
import com.tnd.pw.strategy.common.representations.VisionRepresentation;
import com.tnd.pw.strategy.common.requests.VisionRequest;
import com.tnd.pw.strategy.runner.service.VisionHandlerBuz;
import com.tnd.pw.strategy.vision.exception.VisionComponentNotFoundException;
import com.tnd.pw.strategy.vision.exception.VisionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class VisionHandler implements BaseHandler {
    @Autowired
    private VisionHandlerBuz visionHandlerBuz;

    @HandlerService(path = "/strategy/vision/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionRepresentation> addVision(BaseRequest<VisionRequest> request) throws IOException, DBServiceException {
        VisionRepresentation response = visionHandlerBuz.addVision(request.getData());
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionRepresentation> updateVision(BaseRequest<VisionRequest> request) throws DBServiceException, VisionNotFoundException, IOException {
        VisionRepresentation response = visionHandlerBuz.updateVision(request.getData());
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionRepresentation> getVision(BaseRequest<VisionRequest> request) throws DBServiceException, IOException {
        VisionRepresentation response = visionHandlerBuz.getVision(request.getData());
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/add", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionComponentRep> addVisionComponent(BaseRequest<VisionRequest> request) throws IOException, DBServiceException {
        VisionComponentRep response = visionHandlerBuz.addVisionComponent(request.getData());
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component/update", protocol = "POST",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<VisionComponentRep> updateVisionComponent(BaseRequest<VisionRequest> request) throws DBServiceException, IOException, VisionComponentNotFoundException {
        VisionComponentRep response = visionHandlerBuz.updateVisionComponent(request.getData());
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/strategy/vision_component", protocol = "GET",
            dataRequestType = "com.tnd.pw.strategy.common.requests.VisionRequest")
    public BaseResponse<ListVisionComponentRep> getVisionComponent(BaseRequest<VisionRequest> request) throws DBServiceException, IOException {
        ListVisionComponentRep response = visionHandlerBuz.getVisionComponent(request.getData());
        return new BaseResponse<>(response);
    }
}
