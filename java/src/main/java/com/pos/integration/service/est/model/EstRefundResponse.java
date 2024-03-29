package com.pos.integration.service.est.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EstRefundResponse {

    private final String response;
    private final String errorCode;
    private final String message;
    private final String rawBody;

    public boolean isSuccess() {
        return "[refund-ok]".equals(response);
    }
}
