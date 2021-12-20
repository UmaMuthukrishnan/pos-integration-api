package com.pos.integration.model.refund;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefundResponse {

    private final Boolean isSuccess;
    private final String resultCode;
    private final String resultMessage;
    private final String bankReferenceNumber;
    private final String rawResponse;

    @Builder
    public RefundResponse(Boolean isSuccess, String resultCode, String resultMessage, String bankReferenceNumber, String rawResponse) {
        this.isSuccess = isSuccess;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.bankReferenceNumber = bankReferenceNumber;
        this.rawResponse = rawResponse;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }
}
