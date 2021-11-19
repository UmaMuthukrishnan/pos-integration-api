package com.trendyol.posintegrationapi.model.redirect_url

import com.trendyol.posintegrationapi.model.common.Currency
import com.trendyol.posintegrationapi.model.common.PosCredential
import java.math.BigDecimal

open class RedirectUrlRequest {
    var posCredential: PosCredential? = null
    var amount: BigDecimal? = null
    var currency: Currency? = null
    var referenceNumber: String? = null
    var countryCode: String? = null
    var callbackUrl: String? = null
}
