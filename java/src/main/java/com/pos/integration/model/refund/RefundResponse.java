package com.pos.integration.model.refund;

public class RefundResponse {

    private Boolean isSuccess;
    private String resultCode;
    private String resultMessage;
    private String bankReferenceNumber;
    private String rawResponse;

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

    public String getBankReferenceNumber() {
        return bankReferenceNumber;
    }

    public void setBankReferenceNumber(String bankReferenceNumber) {
        this.bankReferenceNumber = bankReferenceNumber;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }
}
