package com.pos.integration.model.auth;

import com.pos.integration.model.common.Currency;
import com.pos.integration.model.common.PosCredential;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AuthRequest {

    private final String referenceNumber;
    private final PosCredential posCredential;

    private final BigDecimal amount;
    private final Currency currency;

    private final String cardNumber;
    private final int expireMonth;
    private final int expireYear;
    private final String cvv;
}

