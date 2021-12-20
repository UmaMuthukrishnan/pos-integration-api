package com.pos.integration.controller;

import java.math.BigDecimal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pos.integration.model.PostResponse;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.service.adyen.model.AdyenRefundResponse;
import com.pos.integration.service.client.RestClient;
import com.pos.integration.service.est.model.EstAuthResponse;
import com.pos.integration.service.est.model.EstRefundResponse;
import com.pos.integration.service.tybank.model.TyBankAuthResponse;
import com.pos.integration.service.tybank.model.TyBankRefundResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;

import static com.pos.integration.model.common.Currency.EUR;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class PosIntegrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestClient restClient;

    private static final String PAYMENT_PATH = "/makePayment";
    private static final String SOFORT_REFUND_PATH = PAYMENT_PATH + "/refundWithAdyenSofort";
    private static final String SOFORT_REDIRECT_PATH = PAYMENT_PATH + "/getRedirectionUrlWithAdyenSofort";
    private static final String EST_REFUND_PATH = PAYMENT_PATH + "/refundWithEst";
    private static final String EST_AUTH_PATH = PAYMENT_PATH + "/authWithEst";
    private static final String TYBANK_REFUND_PATH = PAYMENT_PATH + "/refundWithTyBank";
    private static final String TYBANK_AUTH_PATH = PAYMENT_PATH + "/authWithTyBank";


    @Test
    @DisplayName("Check for Adyen Refund")
    void refundWithAdyenSofort() throws Exception {

        final RefundRequest request = RefundRequest.builder().refundReferenceNumber("1234")
                .amount(BigDecimal.valueOf(100L)).currency(EUR).build();

        final AdyenRefundResponse adyenRefundResponse =
                AdyenRefundResponse.builder().response("[refund-received]")
                        .message("OK").pspReference("1234").errorCode("").build();

        final PostResponse<AdyenRefundResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(adyenRefundResponse);
        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenReturn(postResponse);

       mockMvc.perform(
                post(SOFORT_REFUND_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(getValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.isSuccess", equalTo(Boolean.TRUE)))
               .andExpect(jsonPath("$.resultMessage", equalTo("OK")));
    }


    @Test
    @DisplayName("Check for Adyen Refund when RestClientException occurs")
    void refundWithAdyenSofortWhenException() throws Exception {
        final RefundRequest request = RefundRequest.builder().refundReferenceNumber("1234")
                .amount(BigDecimal.valueOf(100L)).currency(EUR).build();
        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        mockMvc.perform(
                post(SOFORT_REFUND_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Fetch Redirection Url during Successful attempt")
    void fetchRedirectionUrlFromSofort() throws Exception {

        final RedirectUrlRequest request = RedirectUrlRequest.builder()
                .amount(BigDecimal.valueOf(200)).currency(EUR).callbackUrl("http://callback.url").countryCode("NL")
                .build();
        final RedirectUrlResponse redirectUrlResponse = new RedirectUrlResponse("google.com", "rawbody");

        final PostResponse<RedirectUrlResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(redirectUrlResponse);

        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenReturn(postResponse);

        mockMvc.perform(
                post(SOFORT_REDIRECT_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.url", equalTo(redirectUrlResponse.getUrl())))
                .andExpect(jsonPath("$.rawBody", equalTo(redirectUrlResponse.getRawBody())));
    }

    @Test
    @DisplayName("Fetch Redirection Url when exception occurs")
    void fetchRedirectionUrlWhenExceptionOccurs() throws Exception {

        final RedirectUrlRequest request = RedirectUrlRequest.builder()
                .amount(BigDecimal.valueOf(200)).currency(EUR).callbackUrl("http://callback.url").countryCode("NL")
                .build();

        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        mockMvc.perform(
                post(SOFORT_REDIRECT_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Check for Est Refund")
    void refundWithEst() throws Exception {
        final RefundRequest request = RefundRequest.builder().refundReferenceNumber("1234")
                .amount(BigDecimal.valueOf(100L)).currency(EUR).build();

        final EstRefundResponse estRefundResponse = EstRefundResponse.builder()
                .response("[refund-ok]").message("Successful").errorCode("").build();

        final PostResponse<EstRefundResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(estRefundResponse);

        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenReturn(postResponse);

        mockMvc.perform(
                post(EST_REFUND_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.isSuccess", equalTo(Boolean.TRUE)))
                .andExpect(jsonPath("$.resultMessage", equalTo(estRefundResponse.getMessage())));
    }

    @Test
    @DisplayName("Check for Est Refund  when RestClientException occurs")
    void refundWithEstWhenRestClientExceptionOccurs() throws Exception {
        final RefundRequest request = RefundRequest.builder().refundReferenceNumber("1234")
                .amount(BigDecimal.valueOf(100L)).currency(EUR).build();

        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        mockMvc.perform(
                post(EST_REFUND_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().isBadRequest());


    }

    @Test
    @DisplayName("Validate Est Auth")
    void validateAuthWithEst() throws Exception {
        final AuthRequest request = AuthRequest.builder()
                .amount(BigDecimal.valueOf(500))
                .cardNumber("ABCDEFG")
                .currency(EUR)
                .expireYear(2025)
                .referenceNumber("12345")
                .build();
        final EstAuthResponse estAuthResponse =EstAuthResponse.builder()
                .response("Approved").authCode("00").orderId("123").build();

        final PostResponse<EstAuthResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(estAuthResponse);

        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenReturn(postResponse);


        mockMvc.perform(
                post(EST_AUTH_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.isSuccess", equalTo(Boolean.TRUE)))
                .andExpect(jsonPath("$.authCode", equalTo(estAuthResponse.getAuthCode())));
    }

    @Test
    @DisplayName("Validate Est Auth when RestClientException occurs")
    void validateAuthWithEstWhenRestClientExceptionOccurs() throws Exception {
        final AuthRequest request = AuthRequest.builder()
                .amount(BigDecimal.valueOf(500))
                .cardNumber("ABCDEFG")
                .currency(EUR)
                .expireYear(2025)
                .referenceNumber("12345")
                .build();

        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);


        mockMvc.perform(
                post(EST_AUTH_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Check for TyBank Refund")
    void checkRefundWithTyBank() throws Exception {
        final RefundRequest request = RefundRequest.builder().refundReferenceNumber("1234")
                .amount(BigDecimal.valueOf(100L)).currency(EUR).build();
        final TyBankRefundResponse refundResponse = TyBankRefundResponse.builder()
                .response("[refund-ok]").message("OK").build();

        final PostResponse<TyBankRefundResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(refundResponse);
        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenReturn(postResponse);

        mockMvc.perform(
                post(TYBANK_REFUND_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.isSuccess", equalTo(Boolean.TRUE)))
                .andExpect(jsonPath("$.resultMessage", equalTo(refundResponse.getMessage())));

    }

    @Test
    @DisplayName("Check for TyBank Refund  when RestClientException occurs")
    void checkRefundWithTyBankWhenRestClientExceptionOccurs() throws Exception {
        final RefundRequest request = RefundRequest.builder().refundReferenceNumber("1234")
                .amount(BigDecimal.valueOf(100L)).currency(EUR).build();
        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);

        mockMvc.perform(
                post(TYBANK_REFUND_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Validate TyBank Auth")
    void validateAuthWithTyBank() throws Exception {
        final AuthRequest request = AuthRequest.builder()
                .amount(BigDecimal.valueOf(500))
                .cardNumber("ABCDEFG")
                .currency(EUR)
                .expireYear(2025)
                .referenceNumber("12345")
                .build();
        final TyBankAuthResponse tyBankAuthResponse = TyBankAuthResponse.builder()
                .authCode("00").response("Approved").build();
        final PostResponse<TyBankAuthResponse> postResponse = new PostResponse<>();
        postResponse.setResponse(tyBankAuthResponse);

        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenReturn(postResponse);

        mockMvc.perform(
                post(TYBANK_AUTH_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.isSuccess", equalTo(Boolean.TRUE)))
                .andExpect(jsonPath("$.authCode", equalTo(tyBankAuthResponse.getAuthCode())));
    }

    @Test
    @DisplayName("Validate TyBank Auth when RestClientException occurs")
    void validateAuthWithTyBankWhenRestClientExceptionOccurs() throws Exception {
        final AuthRequest request = AuthRequest.builder()
                .amount(BigDecimal.valueOf(500))
                .cardNumber("ABCDEFG")
                .currency(EUR)
                .expireYear(2025)
                .referenceNumber("12345")
                .build();

        when(restClient.post(
                any(String.class),
                any(Object.class),
                any(ParameterizedTypeReference.class))).thenThrow(RestClientException.class);


        mockMvc.perform(
                post(TYBANK_AUTH_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(getValueAsString(request)))
                .andExpect(status().isBadRequest());
    }




    private String getValueAsString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
