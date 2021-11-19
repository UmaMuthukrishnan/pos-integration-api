package com.trendyol.posintegrationapi.service.est.model

class EstAuthResponse {
    var orderId: String? = null
    var groupId: String? = null
    var response: String? = null
    var authCode: String? = null
    var hostRefNum: String? = null
    var procReturnCode: String? = null
    var transactionId: String? = null
    var errMsg: String? = null
    var rawBody: String? = null
    var isSuccess: Boolean = "Approved" == response
}
