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
import com.pos.integration.service.adyen.model.AdyenRefundResponse;
import com.pos.integration.service.est.model.EstAuthResponse;
import com.pos.integration.service.est.model.EstRefundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EstPosService implements PosService {

    @Autowired
    private RestTemplate rt;


    @Override
    public RefundResponse refund(RefundRequest request) throws PosException {

        try {
            PostResponse<EstRefundResponse> postResponse = post("https://www.est.com/pos/api/v1/refund", request, new ParameterizedTypeReference<PostResponse<EstRefundResponse>>() {});
            EstRefundResponse response = postResponse.getResponse();
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
            PostResponse<EstAuthResponse> postResponse = post("https://www.est.com/pos/api/v1/auth", request, new ParameterizedTypeReference<PostResponse<EstAuthResponse>>() {});
            EstAuthResponse response = postResponse.getResponse();
            return new AuthResponse(
                    response.isSuccess(),
                    response.getProcReturnCode(),
                    response.getErrMsg(),
                    response.getTransactionId(),
                    response.getRawBody(),
                    response.getAuthCode(),
                    response.getHostRefNum()
            );
        } catch (Exception e) {
            throw new PosException(
                    String.format("Error occurred when selling transaction, ReferenceId: %s. Message: %s",
                            request.getReferenceNumber(), e.getMessage()), e);
        }
    }

    @Override
    public RedirectUrlResponse fetchRedirectionUrl(RedirectUrlRequest request) throws PosException {
        throw new UnsupportedOperationException("Unsupported auth operation for Est Pos");
    }

    private <T> PostResponse<T> post(String url, Object request, ParameterizedTypeReference<PostResponse<T>> typeReference) {

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(request);
        ResponseEntity<PostResponse<T>> response = rt.exchange(url, HttpMethod.POST, httpEntity, typeReference);
        return response.getBody();
    }
}
