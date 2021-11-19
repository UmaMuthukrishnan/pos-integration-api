package com.trendyol.posintegrationapi.service.adyen.sofort.model

class AdyenRefundResponse {
    var pspReference: String? = null
    var response: String? = null
    var errorCode: String? = null
    var message: String? = null
    var rawBody: String? = null
    val isSuccess: Boolean
        get() = "[refund-received]" == response
}
