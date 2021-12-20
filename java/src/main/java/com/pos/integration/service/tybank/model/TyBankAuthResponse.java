package com.pos.integration.service.tybank.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TyBankAuthResponse {

    private final String response;

    private final String authCode;

    private final String transactionId;

    private final String errMsg;

    private final String procReturnCode;

    private final String rawBody;

    public boolean isSuccess() {
        return "Approved".equals(response);
    }


}
