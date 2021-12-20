package com.pos.integration.service.est;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.PostResponse;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.auth.AuthResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.service.PosService;
import com.pos.integration.service.client.RestClient;
import com.pos.integration.service.est.model.EstAuthResponse;
import com.pos.integration.service.est.model.EstRefundResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstPosService implements PosService {

    private final RestClient restClient;

    @Value("${est.refundUrl}")
    private String refundUrl;

    @Value("${est.authUrl}")
    private String authUrl;


    @Override
    public RefundResponse refund(RefundRequest request) throws PosException {
        try {
            final PostResponse<EstRefundResponse> postResponse = restClient.post(refundUrl, request, new ParameterizedTypeReference<>() {
            });
            final EstRefundResponse response = postResponse.getResponse();
            return new RefundResponse(
                    response.isSuccess(),
                    response.getErrorCode(),
                    response.getMessage(),
                    null,
                    response.getRawBody());
        } catch (Exception e) {
            throw new PosException(String.format("Error occurred when refunding transaction, ReferenceId:%s. Message: %s", request.getRefundReferenceNumber(), e.getMessage()), e);
        }
    }

    @Override
    public AuthResponse auth(AuthRequest request) throws PosException {
        try {
            final PostResponse<EstAuthResponse> postResponse = restClient.post(authUrl, request, new ParameterizedTypeReference<>() {
            });
            final EstAuthResponse response = postResponse.getResponse();
            return new AuthResponse(
                    response.isSuccess(),
                    response.getProcReturnCode(),
                    response.getErrMsg(),
                    response.getTransactionId(),
                    response.getRawBody(),
                    response.getAuthCode(),
                    response.getHostRefNum());
        } catch (Exception e) {
            throw new PosException(
                    String.format("Error occurred when selling transaction, ReferenceId: %s. Message: %s",
                            request.getReferenceNumber(), e.getMessage()), e);
        }
    }

    @Override
    public RedirectUrlResponse fetchRedirectionUrl(RedirectUrlRequest request) {
        throw new UnsupportedOperationException("Unsupported auth operation for Est Pos");
    }
}
