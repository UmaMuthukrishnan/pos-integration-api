package com.pos.integration.service.est;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.PostResponse;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.auth.AuthResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.service.client.RestClient;
import com.pos.integration.service.config.TestConfig;
import com.pos.integration.service.est.model.EstAuthResponse;
import com.pos.integration.service.est.model.EstRefundResponse;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EstPosServiceTest extends TestConfig {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RestClient restClient;

    @InjectMocks
    private EstPosService posService;

    @Value("${est.refundUrl}")
    private String refundUrl;

    @Value("${est.authUrl}")
    private String authUrl;


    @BeforeEach
    void setUp() {
        restClient = new RestClient(restTemplate);
        posService = new EstPosService(restClient);
        ReflectionTestUtils.setField(posService, "refundUrl", refundUrl);
        ReflectionTestUtils.setField(posService, "authUrl", authUrl);

    }


    @Test
    @DisplayName("Check for Est Refund")
    void refund_When_Successfully() throws PosException {
        final RefundRequest request = Mockito.mock(RefundRequest.class);
        final EstRefundResponse estRefundResponse = EstRefundResponse.builder()
                .response("[refund-ok]").message("OK").errorCode("").build();

        final PostResponse<EstRefundResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(estRefundResponse);

        final ResponseEntity<PostResponse<EstRefundResponse>> entityResp = new ResponseEntity<>(postResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entityResp);

        final RefundResponse refundResponse = posService.refund(request);

        assertThat(refundResponse.getSuccess()).isEqualTo(estRefundResponse.isSuccess());
        assertThat(refundResponse.getResultMessage()).isEqualTo(estRefundResponse.getMessage());
    }

    @Test
    @DisplayName("Check for Est Refund  when RestClientException occurs")
    void checkRefundWhenRestClientExceptionOccurs() {
        final RefundRequest request = mock(RefundRequest.class);
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
    @DisplayName("Validate Est Auth")
    void auth_When_Successfully() throws PosException {
        final AuthRequest request = Mockito.mock(AuthRequest.class);
        final EstAuthResponse estAuthResponse =EstAuthResponse.builder()
                .response("Approved").authCode("00").orderId("123").build();

        final PostResponse<EstAuthResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(estAuthResponse);

        final ResponseEntity<PostResponse<EstAuthResponse>> entityResp = new ResponseEntity<>(postResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenReturn(entityResp);

        final AuthResponse response = posService.auth(request);

        assertTrue(response.getSuccess());
        assertThat(response.getAuthCode()).isEqualTo(estAuthResponse.getAuthCode());
    }

    @Test
    @DisplayName("Validate Est Auth when RestClientException occurs")
    void validateAuthWhenRestClientExceptionOccurs() {
        final AuthRequest request = mock(AuthRequest.class);
        when(restTemplate.exchange(
                any(String.class),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        assertThatThrownBy(() -> posService.auth(request))
                .isInstanceOf(PosException.class)
                .hasMessageContaining("Error occurred when selling transaction");
    }

    @Test
    @DisplayName("Fetch Redirection Url for Est")
    void fetchRedirectionUrl() {
        final RedirectUrlRequest request = Mockito.mock(RedirectUrlRequest.class);
        assertThatThrownBy(() -> posService.fetchRedirectionUrl(request))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("Unsupported auth operation for Est Pos");
    }
}
