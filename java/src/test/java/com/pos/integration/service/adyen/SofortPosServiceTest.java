package com.pos.integration.service.adyen;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.PostResponse;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.service.adyen.model.AdyenRefundResponse;
import com.pos.integration.service.adyen.sofort.SofortPosService;
import com.pos.integration.service.client.RestClient;
import com.pos.integration.service.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SofortPosServiceTest extends TestConfig {

    @Mock
    private RestTemplate restTemplate;


    @InjectMocks
    private RestClient restClient;

    @InjectMocks
    private SofortPosService posService;

    @Value("${sofort.refundUrl}")
    private String refundUrl;

    @Value("${sofort.redirectionUrl}")
    private String redirectionUrl;

    @BeforeEach
    void setUp() {
        restClient = new RestClient(restTemplate);
        posService = new SofortPosService(restClient);
        ReflectionTestUtils.setField(posService, "refundUrl", refundUrl);
        ReflectionTestUtils.setField(posService, "redirectionUrl", redirectionUrl);

    }

    @Test
    @DisplayName("Check for Adyen Refund")
    void refund_When_Successfully() throws PosException {
        final RefundRequest request = Mockito.mock(RefundRequest.class);
        final AdyenRefundResponse adyenRefundResponse =
                AdyenRefundResponse.builder().response("[refund-received]")
                        .message("OK").pspReference("1234").errorCode("").build();

        final PostResponse<AdyenRefundResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(adyenRefundResponse);

        final ResponseEntity<PostResponse<AdyenRefundResponse>> entityResp = new ResponseEntity<>(postResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entityResp);

        final RefundResponse refundResponse = posService.refund(request);
        assertThat(refundResponse.getSuccess()).isEqualTo(adyenRefundResponse.isSuccess());
        assertThat(refundResponse.getResultMessage()).isEqualTo(adyenRefundResponse.getMessage());
    }


    @Test
    @DisplayName("Check for Adyen Refund when RestClientException occurs")
    void refund_When_Exception() {
        final RefundRequest request = Mockito.mock(RefundRequest.class);
        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        assertThatThrownBy(() -> posService.refund(request))
                .isInstanceOf(PosException.class)
                .hasMessageContaining("Error occurred when refunding transaction");
    }

    @Test
    @DisplayName("Fetch Redirection Url during Successful attempt")
    void fetchRedirectionUrl_When_Successfully() throws PosException {

        final RedirectUrlRequest request = Mockito.mock(RedirectUrlRequest.class);
        final RedirectUrlResponse redirectUrlResponse = new RedirectUrlResponse("google.com", "rawbody");

        final PostResponse<RedirectUrlResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(redirectUrlResponse);

        final ResponseEntity<PostResponse<RedirectUrlResponse>> entityResp = new ResponseEntity<PostResponse<RedirectUrlResponse>>(postResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entityResp);

        final RedirectUrlResponse response = posService.fetchRedirectionUrl(request);

        assertThat(response.getUrl()).isEqualTo(redirectUrlResponse.getUrl());
        assertThat(response.getRawBody()).isEqualTo(redirectUrlResponse.getRawBody());
    }

    @Test
    @DisplayName("Fetch Redirection Url when exception occurs")
    void fetchRedirectionUrl_When_Exception_Occurs() {

        final RedirectUrlRequest request = Mockito.mock(RedirectUrlRequest.class);
        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        assertThatThrownBy(() -> posService.fetchRedirectionUrl(request))
                .isInstanceOf(PosException.class)
                .hasMessageContaining("Error occurred when getting payment redirect url for sofort");
    }

    @Test
    @DisplayName("Validate Adyen Sofort Auth")
    void validateAuth() {
        final AuthRequest request = Mockito.mock(AuthRequest.class);
        assertThatThrownBy(() -> posService.auth(request))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Unsupported auth operation for Adyen Sofort Pos");
    }
}
