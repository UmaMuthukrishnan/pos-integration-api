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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SofortPosService implements PosService {

    private final String SERVICE_URL = "https://www.adyen.com/api/v1";

    @Autowired
    private RestTemplate rt;

    @Override
    public RefundResponse refund(RefundRequest request) throws PosException {

        try {
            PostResponse<AdyenRefundResponse> postResponse = post(SERVICE_URL + "/refund", request, new ParameterizedTypeReference<PostResponse<AdyenRefundResponse>>() {});
            AdyenRefundResponse response = postResponse.getResponse();
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
            PostResponse<RedirectUrlResponse> postResponse = post(SERVICE_URL + "/redirection/url", request, new ParameterizedTypeReference<PostResponse<RedirectUrlResponse>>() {});
            return new RedirectUrlResponse( postResponse.getResponse().getUrl(), postResponse.getResponse().getRawBody() );
        } catch (Exception e) {
            throw new PosException(String.format("Error occurred when getting payment redirect url for sofort, ReferenceId:%s. Message: %s", request.getReferenceNumber(), e.getMessage()), e);
        }
    }

    private <T> PostResponse<T> post(String url, Object request, ParameterizedTypeReference<PostResponse<T>> typeReference) {

        HttpEntity<Object> httpEntity = new HttpEntity<Object>(request);
        ResponseEntity<PostResponse<T>> response = rt.exchange(url, HttpMethod.POST, httpEntity, typeReference);
        return response.getBody();
    }
}
