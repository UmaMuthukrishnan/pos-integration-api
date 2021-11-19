package com.pos.integration.service.tybank;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.auth.AuthResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.service.PosService;

public class TyBankPosService implements PosService {

    @Override
    public RefundResponse refund(RefundRequest request) throws PosException {
        // TODO: business implementation section
        // "refund" flow provides to refund the amount to user via TyBank virtual pos.
        // The user will see refunded amount in his or her account in 1-2 days.
        return null;
    }

    @Override
    public AuthResponse auth(AuthRequest request) throws PosException {
        // TODO: business implementation section
        // "auth" flow enables the user to make payment via credit card from TyBank virtual pos.
        // This payment method captures the amount from the user's credit card directly.
        return null;
    }

    @Override
    public RedirectUrlResponse fetchRedirectionUrl(RedirectUrlRequest request) throws PosException {
        return null;
    }
}
