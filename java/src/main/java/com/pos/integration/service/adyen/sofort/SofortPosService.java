package com.pos.integration.service.adyen.sofort;

import com.pos.integration.model.PostResponse;
import com.pos.integration.service.PosService;
import com.pos.integration.exception.PosException;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.auth.AuthResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.service.adyen.model.AdyenRefundResponse;
import com.pos.integration.service.client.RestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SofortPosService implements PosService {

    private final RestClient restClient;

    @Value("${sofort.refundUrl}")
    private String refundUrl;

    @Value("${sofort.redirectionUrl}")
    private String redirectionUrl;


    @Override
    public RefundResponse refund(RefundRequest request) throws PosException {
        try {
            final PostResponse<AdyenRefundResponse> postResponse = restClient.post(refundUrl, request, new ParameterizedTypeReference<>() {
            });
            final AdyenRefundResponse response = postResponse.getResponse();
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
        throw new UnsupportedOperationException("Unsupported auth operation for Adyen Sofort Pos");
    }

    @Override
    public RedirectUrlResponse fetchRedirectionUrl(RedirectUrlRequest request) throws PosException {

        try {
            final PostResponse<RedirectUrlResponse> postResponse = restClient.post(redirectionUrl, request, new ParameterizedTypeReference<>() {
            });
            return new RedirectUrlResponse(postResponse.getResponse().getUrl(), postResponse.getResponse().getRawBody());
        } catch (Exception e) {
            throw new PosException(String.format("Error occurred when getting payment redirect url for sofort, ReferenceId:%s. Message: %s", request.getReferenceNumber(), e.getMessage()), e);
        }
    }
}
