package com.tnd.pw.strategy.runner.service.impl;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.sdk.ActionServiceSdkClient;
import com.tnd.pw.strategy.runner.exception.ActionServiceFailedException;
import org.springframework.beans.factory.annotation.Autowired;

public class SdkService {
    @Autowired
    private ActionServiceSdkClient actionServiceSdkClient;

    public CsActionRepresentation getTodoComment(Long belongId) throws ActionServiceFailedException {
        BaseResponse<CsActionRepresentation> response = actionServiceSdkClient.getTodoComments(belongId);
        if(response.getResponseCode() < 1 ) {
            throw new ActionServiceFailedException();
        }
        return response.getData();
    }
}
