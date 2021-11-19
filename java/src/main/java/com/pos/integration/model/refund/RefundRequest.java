package com.pos.integration.model.refund;

import com.pos.integration.model.common.Currency;
import com.pos.integration.model.common.PosCredential;

import java.math.BigDecimal;


public class RefundRequest {

    private PosCredential posCredential;

    private BigDecimal amount;
    private Currency currency;
    private String refundReferenceNumber;

    public PosCredential getPosCredential() {
        return posCredential;
    }

    public void setPosCredential(PosCredential posCredential) {
        this.posCredential = posCredential;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getRefundReferenceNumber() {
        return refundReferenceNumber;
    }

    public void setRefundReferenceNumber(String refundReferenceNumber) {
        this.refundReferenceNumber = refundReferenceNumber;
    }
}
