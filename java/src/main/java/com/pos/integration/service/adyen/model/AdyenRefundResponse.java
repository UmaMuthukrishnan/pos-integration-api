package com.pos.integration.service.adyen.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdyenRefundResponse {

    private final String pspReference;
    private final String response;
    private final String errorCode;
    private final String message;
    private final String rawBody;

    public boolean isSuccess() {
        return "[refund-received]".equals(response);
    }
}
