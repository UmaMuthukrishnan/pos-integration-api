package com.pos.integration.service.adyen;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.PostResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.service.adyen.model.AdyenRefundResponse;
import com.pos.integration.service.adyen.sofort.SofortPosService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SofortPosServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SofortPosService posService = new SofortPosService();

    @Test
    void refund_When_Successfully() throws PosException {

        RefundRequest request = Mockito.mock(RefundRequest.class);

        AdyenRefundResponse adyenRefundResponse = new AdyenRefundResponse();
        adyenRefundResponse.setResponse("[refund-received]");
        adyenRefundResponse.setMessage("OK");
        adyenRefundResponse.setPspReference("1234");
        adyenRefundResponse.setErrorCode("");

        PostResponse<AdyenRefundResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(adyenRefundResponse);

        ResponseEntity<PostResponse<AdyenRefundResponse>> entityResp = new ResponseEntity<PostResponse<AdyenRefundResponse>>(postResponse, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entityResp);

        RefundResponse refundResponse = posService.refund(request);

        assertEquals(refundResponse.getSuccess(), adyenRefundResponse.isSuccess());
        assertEquals(refundResponse.getResultMessage(), adyenRefundResponse.getMessage());
    }

    @Test
    void fetchRedirectionUrl_When_Successfully() throws PosException {

        RedirectUrlRequest request = Mockito.mock(RedirectUrlRequest.class);
        RedirectUrlResponse redirectUrlResponse = new RedirectUrlResponse("google.com", "rawbody");

        PostResponse<RedirectUrlResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(redirectUrlResponse);

        ResponseEntity<PostResponse<RedirectUrlResponse>> entityResp = new ResponseEntity<PostResponse<RedirectUrlResponse>>(postResponse, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entityResp);

        RedirectUrlResponse response = posService.fetchRedirectionUrl(request);

        assertEquals(response.getUrl(), redirectUrlResponse.getUrl());
        assertEquals(response.getRawBody(), redirectUrlResponse.getRawBody());
    }
}
