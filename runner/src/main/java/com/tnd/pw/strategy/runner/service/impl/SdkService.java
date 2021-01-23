package com.tnd.pw.strategy.runner.service.impl;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.sdk.ActionServiceSdkClient;
import com.tnd.pw.development.common.representations.CsDevRepresentation;
import com.tnd.pw.development.sdk.DevServiceSdkClient;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import com.tnd.pw.strategy.runner.exception.DevServiceFailedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SdkService {
    @Autowired
    private ActionServiceSdkClient actionServiceSdkClient;
    @Autowired
    private DevServiceSdkClient devServiceSdkClient;

    public CsActionRepresentation getTodoComment(Long belongId) throws ActionServiceFailedException {
        BaseResponse<CsActionRepresentation> response = actionServiceSdkClient.getTodoComments(belongId);
        if(response.getResponseCode() < 1 ) {
            throw new ActionServiceFailedException();
        }
        return response.getData();
    }

    public CsDevRepresentation getReleases(Long productId) throws DevServiceFailedException {
        BaseResponse<CsDevRepresentation> response = devServiceSdkClient.getReleaseByProductId(productId);
        if(response.getResponseCode() < 1 ) {
            throw new DevServiceFailedException();
        }
        return response.getData();
    }

    public CsDevRepresentation getFeatures(List<Long> initiativeIds) throws DevServiceFailedException {
        BaseResponse<CsDevRepresentation> response = devServiceSdkClient.getFeatureByInitiativeIds(initiativeIds);
        if(response.getResponseCode() < 1 ) {
            throw new DevServiceFailedException();
        }
        return response.getData();
    }

}
