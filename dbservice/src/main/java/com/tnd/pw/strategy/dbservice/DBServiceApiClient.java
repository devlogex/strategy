package com.tnd.pw.strategy.dbservice;

import com.tnd.common.api.client.service.AbstractService;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.common.constants.Protocol;
import com.tnd.dbservice.common.representation.DBServiceRequest;
import com.tnd.dbservice.common.representation.DBServiceResponse;

import java.io.IOException;

public class DBServiceApiClient extends AbstractService {

    public BaseResponse<DBServiceResponse.QueryResult> selectSQL(String url, DBServiceRequest request) throws IOException {
        return client.sendRequest(url, Protocol.GET.name(), request);
    }

    public BaseResponse<Boolean> executeSQL(String url, DBServiceRequest request) throws IOException {
        return client.sendRequest(url, Protocol.GET.name(), request);
    }
}
