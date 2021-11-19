package com.trendyol.posintegrationapi.service.adyen

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.trendyol.posintegrationapi.model.PostResponse
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlRequest
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlResponse
import com.trendyol.posintegrationapi.model.refund.RefundRequest
import com.trendyol.posintegrationapi.service.adyen.sofort.SofortPosService
import com.trendyol.posintegrationapi.service.adyen.sofort.model.AdyenRefundResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
class SofortPosServiceTest {
    @Mock
    private val restTemplate: RestTemplate? = null

    @InjectMocks
    private val posService = SofortPosService()

    @Test
    fun refund_When_Successfully() {
        val request = mock<RefundRequest>()
        val adyenRefundResponse = AdyenRefundResponse()
        adyenRefundResponse.response = "[refund-received]"
        adyenRefundResponse.message = "OK"
        adyenRefundResponse.pspReference = "1234"
        adyenRefundResponse.errorCode = ""
        val postResponse = PostResponse<AdyenRefundResponse>()
        postResponse.setResponse(adyenRefundResponse)
        val entityResp = ResponseEntity(postResponse, HttpStatus.OK)
        `when`<ResponseEntity<*>>(
            restTemplate?.exchange(
                any<String>(), any(), any(), any<ParameterizedTypeReference<*>>()
            )
        ).thenReturn(entityResp)
        val refundResponse = posService.refund(request)
        assertEquals(refundResponse!!.success, adyenRefundResponse.isSuccess)
        assertEquals(refundResponse.resultMessage, adyenRefundResponse.message)
    }

    @Test
    fun fetchRedirectionUrl_When_Successfully() {
        val request = mock<RedirectUrlRequest>()
        val redirectUrlResponse = RedirectUrlResponse("google.com", "rawbody")
        val postResponse = PostResponse<RedirectUrlResponse>()
        postResponse.setResponse(redirectUrlResponse)
        val entityResp = ResponseEntity(postResponse, HttpStatus.OK)
        `when`<ResponseEntity<*>>(
            restTemplate?.exchange(
                any<String>(), any(), any(), any<ParameterizedTypeReference<*>>()
            )
        ).thenReturn(entityResp)
        val response = posService.fetchRedirectionUrl(request)
        assertEquals(response.url, redirectUrlResponse.url)
        assertEquals(response.rawBody, redirectUrlResponse.rawBody)
    }
}