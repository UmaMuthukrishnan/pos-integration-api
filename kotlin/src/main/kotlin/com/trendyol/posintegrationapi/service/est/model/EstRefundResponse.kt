package com.trendyol.posintegrationapi.service.est.model

class EstRefundResponse {
    var response: String? = null
    var errorCode: String? = null
    var message: String? = null
    var rawBody: String? = null
    val isSuccess: Boolean = "[refund-ok]" == response
}
