package com.pos.integration.service;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.auth.AuthResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;

public interface PosService {

    RefundResponse refund(RefundRequest request) throws PosException;

    AuthResponse auth(AuthRequest request) throws PosException;

    RedirectUrlResponse fetchRedirectionUrl(RedirectUrlRequest request) throws PosException;
}
