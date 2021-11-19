package com.trendyol.posintegrationapi.model.auth

open class AuthResponse(
    var success: Boolean?,
    var resultCode: String?,
    var resultMessage: String?,
    var transactionId: String?,
    var rawResponse: String?,
    var authCode: String?,
    var hostReferenceNumber: String?,
)