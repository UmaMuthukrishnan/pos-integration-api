package com.pos.integration.service.tybank;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.PostResponse;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.auth.AuthResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.service.PosService;
import com.pos.integration.service.tybank.model.TyBankAuthResponse;
import com.pos.integration.service.tybank.model.TyBankRefundResponse;
import com.pos.integration.service.client.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TyBankPosService implements PosService {

    private final RestClient restClient;

    @Value("${tyBank.refundUrl}")
    private String refundUrl;

    @Value("${tyBank.authUrl}")
    private String authUrl;


    @Override
    public RefundResponse refund(RefundRequest request) throws PosException {
        try {
            final PostResponse<TyBankRefundResponse> postResponse = restClient.post(refundUrl, request,
                    new ParameterizedTypeReference<>() {});
            final TyBankRefundResponse response = postResponse.getResponse();
            return new RefundResponse(
                    response.isSuccess(),
                    response.getErrorCode(),
                    response.getMessage(),
                    null,
                    response.getRawBody()
            );
        } catch (Exception e) {
            throw new PosException(String.format("Error occurred when refunding transaction, ReferenceId:%s. Message: %s", request.getRefundReferenceNumber(), e.getMessage()), e);
        }
    }

    @Override
    public AuthResponse auth(AuthRequest request) throws PosException {
        try {
            final PostResponse<TyBankAuthResponse> postResponse = restClient.post(authUrl, request, new ParameterizedTypeReference<>() {
            });
            final TyBankAuthResponse response = postResponse.getResponse();
            return new AuthResponse(
                    response.isSuccess(),
                    response.getProcReturnCode(),
                    response.getErrMsg(),
                    response.getTransactionId(),
                    response.getRawBody(),
                    response.getAuthCode(),
                    null
            );
        } catch (Exception e) {
            throw new PosException(
                    String.format("Error occurred when selling transaction, ReferenceId: %s. Message: %s",
                            request.getReferenceNumber(), e.getMessage()), e);
        }
    }

    @Override
    public RedirectUrlResponse fetchRedirectionUrl(RedirectUrlRequest request) {
        throw new UnsupportedOperationException("Unsupported auth operation for TyBank Pos");
    }
}
