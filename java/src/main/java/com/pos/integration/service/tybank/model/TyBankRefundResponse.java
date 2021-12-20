package com.pos.integration.service.tybank.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TyBankRefundResponse {

    private final String response;

    private final String errorCode;

    private final String message;

    private final String rawBody;

    public boolean isSuccess() {
        return "[refund-ok]".equals(response);
    }
}
