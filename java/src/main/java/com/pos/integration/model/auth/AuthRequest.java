package com.pos.integration.model.auth;

import com.pos.integration.model.common.Currency;
import com.pos.integration.model.common.PosCredential;

import java.math.BigDecimal;

public class AuthRequest {

    private String referenceNumber;
    private PosCredential posCredential;

    private BigDecimal amount;
    private Currency currency;

    private String cardNumber;
    private int expireMonth;
    private int expireYear;
    private String cvv;

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(int expireMonth) {
        this.expireMonth = expireMonth;
    }

    public int getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(int expireYear) {
        this.expireYear = expireYear;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}

