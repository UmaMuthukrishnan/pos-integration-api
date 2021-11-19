package com.trendyol.posintegrationapi.model.auth

import com.trendyol.posintegrationapi.model.common.PosCredential
import java.math.BigDecimal
import java.util.*

open class AuthRequest {
    var referenceNumber: String? = null
    var posCredential: PosCredential? = null
    var amount: BigDecimal? = null
    var currency: Currency? = null
    var cardNumber: String? = null
    var expireMonth = 0
    var expireYear = 0
    var cvv: String? = null
}