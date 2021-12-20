package com.pos.integration.service.tybank;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.PostResponse;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.auth.AuthResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.service.config.TestConfig;
import com.pos.integration.service.tybank.model.TyBankAuthResponse;
import com.pos.integration.service.tybank.model.TyBankRefundResponse;
import com.pos.integration.service.client.RestClient;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TyBankPosServiceTest extends TestConfig {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClient restClient;

    private TyBankPosService tyBankPosService;

    @Value("${tyBank.refundUrl}")
    private String refundUrl;

    @Value("${tyBank.authUrl}")
    private String authUrl;


    @BeforeEach
    void setUp() {
        restClient = new RestClient(restTemplate);
        tyBankPosService = new TyBankPosService(restClient);
        ReflectionTestUtils.setField(tyBankPosService, "refundUrl", refundUrl);
        ReflectionTestUtils.setField(tyBankPosService, "authUrl", authUrl);

    }

    @Test
    @DisplayName("Check for TyBank Refund")
    void checkRefundWithTyBank() throws PosException {
        final RefundRequest request = mock(RefundRequest.class);
        final TyBankRefundResponse refundResponse = TyBankRefundResponse.builder()
                .response("[refund-ok]").message("OK").build();

        final PostResponse<TyBankRefundResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(refundResponse);
        ResponseEntity<PostResponse<TyBankRefundResponse>> entityResp = new ResponseEntity<>(postResponse, HttpStatus.OK);
        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entityResp);

        final RefundResponse response = tyBankPosService.refund(request);
        assertThat(response.getSuccess()).isEqualTo(refundResponse.isSuccess());
        assertThat(response.getResultMessage()).isEqualTo(refundResponse.getMessage());

    }

    @Test
    @DisplayName("Check for TyBank Refund  when RestClientException occurs")
    void checkRefundWhenRestClientExceptionOccurs() {
        final RefundRequest request = mock(RefundRequest.class);
        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        assertThatThrownBy(() -> tyBankPosService.refund(request))
                .isInstanceOf(PosException.class)
                .hasMessageContaining("Error occurred when refunding transaction");

    }

    @Test
    @DisplayName("Validate TyBank Auth")
    void validateAuth() throws PosException {
        final AuthRequest request = mock(AuthRequest.class);
        final TyBankAuthResponse tyBankAuthResponse = TyBankAuthResponse.builder()
                .authCode("00").response("Approved").build();
        final PostResponse<TyBankAuthResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(tyBankAuthResponse);

        final ResponseEntity<PostResponse<TyBankAuthResponse>> entityResp = new ResponseEntity<>(postResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entityResp);

        final AuthResponse response = tyBankPosService.auth(request);

        assertTrue(response.getSuccess());
        assertThat(response.getAuthCode()).isEqualTo(tyBankAuthResponse.getAuthCode());
    }

    @Test
    @DisplayName("Validate TyBank Auth when RestClientException occurs")
    void validateAuthWhenRestClientExceptionOccurs() {
        final AuthRequest request = mock(AuthRequest.class);
        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        assertThatThrownBy(() -> tyBankPosService.auth(request))
                .isInstanceOf(PosException.class)
                .hasMessageContaining("Error occurred when selling transaction");
    }

    @Test
    @DisplayName("Fetch Redirection Url for TyBank")
    void fetchRedirectionUrl() {
        final RedirectUrlRequest request = Mockito.mock(RedirectUrlRequest.class);
        assertThatThrownBy(() -> tyBankPosService.fetchRedirectionUrl(request))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Unsupported auth operation for TyBank Pos");
    }
}
