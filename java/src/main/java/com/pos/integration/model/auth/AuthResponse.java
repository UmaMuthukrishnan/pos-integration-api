package com.pos.integration.model.auth;

public class AuthResponse {

    private Boolean isSuccess;
    private String resultCode;
    private String resultMessage;
    private String transactionId;
    private String rawResponse;
    private String authCode;
    private String hostReferenceNumber;

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

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getHostReferenceNumber() {
        return hostReferenceNumber;
    }

    public void setHostReferenceNumber(String hostReferenceNumber) {
        this.hostReferenceNumber = hostReferenceNumber;
    }
}
