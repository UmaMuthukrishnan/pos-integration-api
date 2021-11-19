package com.trendyol.posintegrationapi.service.est

import com.trendyol.posintegrationapi.exception.PosException
import com.trendyol.posintegrationapi.model.PostResponse
import com.trendyol.posintegrationapi.model.auth.AuthRequest
import com.trendyol.posintegrationapi.model.auth.AuthResponse
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlRequest
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlResponse
import com.trendyol.posintegrationapi.model.refund.RefundRequest
import com.trendyol.posintegrationapi.model.refund.RefundResponse
import com.trendyol.posintegrationapi.service.PosService
import com.trendyol.posintegrationapi.service.est.model.EstAuthResponse
import com.trendyol.posintegrationapi.service.est.model.EstRefundResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.lang.String.format

@Service
class EstPosService : PosService {
    @Autowired
    private lateinit var rt: RestTemplate

    override fun refund(request: RefundRequest): RefundResponse? {
        return try {
            val postResponse: PostResponse<EstRefundResponse> = post(
                "https://www.est.com/pos/api/v1/refund",
                request!!,
                object : ParameterizedTypeReference<PostResponse<EstRefundResponse>>() {})
            val response = postResponse.response
            RefundResponse(
                response?.isSuccess,
                response?.errorCode,
                response?.message,
                null,
                response?.rawBody
            )
        } catch (e: Exception) {
            throw PosException(
                java.lang.String.format(
                    "Error occurred when refunding transaction, ReferenceId:%s. Message: %s",
                    request?.refundReferenceNumber,
                    e.message
                ), e
            )
        }
    }

    override fun auth(request: AuthRequest): AuthResponse? {
        return try {
            val postResponse: PostResponse<EstAuthResponse> = post(
                "https://www.est.com/pos/api/v1/auth",
                request!!,
                object : ParameterizedTypeReference<PostResponse<EstAuthResponse>>() {})
            val response = postResponse.response
            AuthResponse(
                response?.isSuccess,
                response?.procReturnCode,
                response?.errMsg,
                response?.transactionId,
                response?.rawBody,
                response?.authCode,
                response?.hostRefNum
            )
        } catch (e: Exception) {
            throw PosException(
                format(
                    "Error occurred when selling transaction, ReferenceId: %s. Message: %s",
                    request.referenceNumber, e.message
                ), e
            )
        }
    }

    override fun fetchRedirectionUrl(request: RedirectUrlRequest): RedirectUrlResponse {
        throw UnsupportedOperationException("Unsupported auth operation for Est Pos")
    }

    private fun <T> post(
        url: String,
        request: Any,
        typeReference: ParameterizedTypeReference<PostResponse<T>>
    ): PostResponse<T> {
        val httpEntity = HttpEntity(request)
        val response = rt.exchange(url, HttpMethod.POST, httpEntity, typeReference)
        return response.body!!
    }
}
