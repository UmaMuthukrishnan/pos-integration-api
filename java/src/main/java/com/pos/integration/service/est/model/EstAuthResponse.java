package com.pos.integration.service.est.model;

public class EstAuthResponse {

    private String orderId;
    private String groupId;
    private String response;
    private String authCode;
    private String hostRefNum;
    private String procReturnCode;
    private String transactionId;
    private String errMsg;
    private String rawBody;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getHostRefNum() {
        return hostRefNum;
    }

    public void setHostRefNum(String hostRefNum) {
        this.hostRefNum = hostRefNum;
    }

    public String getProcReturnCode() {
        return procReturnCode;
    }

    public void setProcReturnCode(String procReturnCode) {
        this.procReturnCode = procReturnCode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getRawBody() {
        return rawBody;
    }

    public void setRawBody(String rawBody) {
        this.rawBody = rawBody;
    }

    public boolean isSuccess() {
        return "Approved".equals(response);
    }
}
