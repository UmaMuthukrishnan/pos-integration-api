package com.trendyol.posintegrationapi.service.est

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.trendyol.posintegrationapi.model.PostResponse
import com.trendyol.posintegrationapi.model.auth.AuthRequest
import com.trendyol.posintegrationapi.model.refund.RefundRequest
import com.trendyol.posintegrationapi.service.est.model.EstAuthResponse
import com.trendyol.posintegrationapi.service.est.model.EstRefundResponse
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
class EstPosServiceTest {

    @Mock
    private lateinit var restTemplate: RestTemplate

    @InjectMocks
    private val posService = EstPosService()

    @Test
    fun refund_When_Successfully() {

        //Arrange
        val request = mock<RefundRequest>()
        val estRefundResponse = EstRefundResponse()
        estRefundResponse.response = "[refund-ok]"
        estRefundResponse.message = "OK"
        estRefundResponse.errorCode = ""

        //Act
        val postResponse = PostResponse<EstRefundResponse>()
        postResponse.setResponse(estRefundResponse)
        val entityResp = ResponseEntity(postResponse, HttpStatus.OK)
        `when`<ResponseEntity<*>>(
            restTemplate?.exchange(
                any<String>(), any(), any(), any<ParameterizedTypeReference<*>>()
            )
        ).thenReturn(entityResp)
        val refundResponse = posService.refund(request)

        //Assert
        assertEquals(refundResponse!!.success, estRefundResponse.isSuccess)
        assertEquals(refundResponse.resultMessage, estRefundResponse.message)
    }

    @Test
    fun auth_When_Successfully() {
        //Arrange
        val request = mock<AuthRequest>()
        val estAuthResponse = EstAuthResponse()
        estAuthResponse.isSuccess=true
        estAuthResponse.response = "Approved"
        estAuthResponse.authCode = "00"
        estAuthResponse.orderId = "123"

        //Act
        val postResponse = PostResponse<EstAuthResponse>()
        postResponse.setResponse(estAuthResponse)
        val entityResp = ResponseEntity(postResponse, HttpStatus.OK)
        `when`<ResponseEntity<*>>(
            restTemplate?.exchange(
                any<String>(), any(), any(), any<ParameterizedTypeReference<*>>()
            )
        ).thenReturn(entityResp)
        val response = posService.auth(request)

        //Assert
        assertTrue(response!!.success!!)
        assertEquals(response.authCode, estAuthResponse.authCode)
    }
}