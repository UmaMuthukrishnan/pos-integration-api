package com.pos.integration.model.redirect_url;

import com.pos.integration.model.common.Currency;
import com.pos.integration.model.common.PosCredential;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class RedirectUrlRequest {
    private final PosCredential posCredential;
    private final BigDecimal amount;
    private final Currency currency;
    private final String referenceNumber;
    private final String countryCode;
    private final String callbackUrl;
}
