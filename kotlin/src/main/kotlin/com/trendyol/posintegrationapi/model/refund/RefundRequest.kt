package com.trendyol.posintegrationapi.model.refund

import com.trendyol.posintegrationapi.model.common.Currency
import com.trendyol.posintegrationapi.model.common.PosCredential
import java.math.BigDecimal

open class RefundRequest {
    var posCredential: PosCredential? = null
    var amount: BigDecimal? = null
    var currency: Currency? = null
    var refundReferenceNumber: String? = null
}
