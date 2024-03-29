package com.pos.integration.service.est.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstAuthResponse {

    private final String orderId;
    private final String groupId;
    private final String response;
    private final String authCode;
    private final String hostRefNum;
    private final String procReturnCode;
    private final String transactionId;
    private final String errMsg;
    private final String rawBody;

    public boolean isSuccess() {
        return "Approved".equals(response);
    }
}
