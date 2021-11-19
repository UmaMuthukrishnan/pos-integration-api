package com.pos.integration.service.est.model;

public class EstRefundResponse {

    private String response;
    private String errorCode;
    private String message;
    private String rawBody;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRawBody() {
        return rawBody;
    }

    public void setRawBody(String rawBody) {
        this.rawBody = rawBody;
    }

    public boolean isSuccess() {
        return "[refund-ok]".equals(response);
    }
}
