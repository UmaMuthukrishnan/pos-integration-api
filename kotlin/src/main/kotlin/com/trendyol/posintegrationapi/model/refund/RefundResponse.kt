package com.trendyol.posintegrationapi.model.refund

class RefundResponse(
    var success: Boolean?,
    var resultCode: String?,
    var resultMessage: String?,
    var bankReferenceNumber: String?,
    var rawResponse: String?,
)
