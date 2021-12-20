package com.pos.integration.model.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthResponse {

    private final Boolean isSuccess;
    private final String resultCode;
    private final String resultMessage;
    private final String transactionId;
    private final String rawResponse;
    private final String authCode;
    private final String hostReferenceNumber;

    @Builder
    public AuthResponse(Boolean isSuccess, String resultCode, String resultMessage, String transactionId,
                        String rawResponse, String authCode, String hostReferenceNumber) {
        this.isSuccess = isSuccess;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.transactionId = transactionId;
        this.rawResponse = rawResponse;
        this.authCode = authCode;
        this.hostReferenceNumber = hostReferenceNumber;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }
}
