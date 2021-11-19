package com.pos.integration.model.redirect_url;

import com.pos.integration.model.common.Currency;
import com.pos.integration.model.common.PosCredential;

import java.math.BigDecimal;

public class RedirectUrlRequest {

    private PosCredential posCredential;

    private BigDecimal amount;
    private Currency currency;

    private String referenceNumber;
    private String countryCode;
    private String callbackUrl;

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

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
