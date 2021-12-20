package com.pos.integration.model.refund;

import com.pos.integration.model.common.Currency;
import com.pos.integration.model.common.PosCredential;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@Builder
public class RefundRequest {

    private final PosCredential posCredential;

    private final BigDecimal amount;
    private final Currency currency;
    private final String refundReferenceNumber;
}
