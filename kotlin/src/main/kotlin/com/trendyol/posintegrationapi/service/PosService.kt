package com.trendyol.posintegrationapi.service

import com.trendyol.posintegrationapi.model.auth.AuthRequest
import com.trendyol.posintegrationapi.model.auth.AuthResponse
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlRequest
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlResponse
import com.trendyol.posintegrationapi.model.refund.RefundRequest
import com.trendyol.posintegrationapi.model.refund.RefundResponse

interface PosService {
    fun refund(request: RefundRequest): RefundResponse?

    fun auth(request: AuthRequest): AuthResponse?

    fun fetchRedirectionUrl(request: RedirectUrlRequest): RedirectUrlResponse?
}
