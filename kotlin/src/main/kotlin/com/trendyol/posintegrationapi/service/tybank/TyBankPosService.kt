package com.trendyol.posintegrationapi.service.tybank

import com.trendyol.posintegrationapi.exception.PosException
import com.trendyol.posintegrationapi.model.auth.AuthRequest
import com.trendyol.posintegrationapi.model.auth.AuthResponse
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlRequest
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlResponse
import com.trendyol.posintegrationapi.model.refund.RefundRequest
import com.trendyol.posintegrationapi.model.refund.RefundResponse
import com.trendyol.posintegrationapi.service.PosService

class TyBankPosService : PosService {
    override fun refund(request: RefundRequest): RefundResponse? {
        // TODO: business implementation section
        // "refund" flow provides to refund the amount to user via TyBank virtual pos.
        // The user will see refunded amount in his or her account in 1-2 days.
        return null
    }

    override fun auth(request: AuthRequest): AuthResponse? {
        // TODO: business implementation section
        // "auth" flow enables the user to make payment via credit card from TyBank virtual pos.
        // This payment method captures the amount from the user's credit card directly.
        return null
    }

    override fun fetchRedirectionUrl(request: RedirectUrlRequest): RedirectUrlResponse? {
        return null
    }
}
