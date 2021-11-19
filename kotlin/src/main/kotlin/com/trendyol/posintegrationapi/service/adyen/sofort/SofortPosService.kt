package com.trendyol.posintegrationapi.service.adyen.sofort

import com.trendyol.posintegrationapi.exception.PosException
import com.trendyol.posintegrationapi.model.PostResponse
import com.trendyol.posintegrationapi.model.auth.AuthRequest
import com.trendyol.posintegrationapi.model.auth.AuthResponse
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlRequest
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlResponse
import com.trendyol.posintegrationapi.model.refund.RefundRequest
import com.trendyol.posintegrationapi.model.refund.RefundResponse
import com.trendyol.posintegrationapi.service.PosService
import com.trendyol.posintegrationapi.service.adyen.sofort.model.AdyenRefundResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SofortPosService : PosService {
    private val SERVICE_URL = "https://www.adyen.com/api/v1"

    @Autowired
    private lateinit var rt: RestTemplate

    override fun refund(request: RefundRequest): RefundResponse? {
        return try {
            val postResponse: PostResponse<AdyenRefundResponse> = post(
                "$SERVICE_URL/refund",
                request,
                object : ParameterizedTypeReference<PostResponse<AdyenRefundResponse>>() {})
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
                    request.refundReferenceNumber,
                    e.message
                ), e
            )
        }
    }

    override fun auth(request: AuthRequest): AuthResponse {
        throw UnsupportedOperationException("Unsupported auth operation for Adyen Sofort Pos")
    }

    override fun fetchRedirectionUrl(request: RedirectUrlRequest): RedirectUrlResponse {
        return try {
            val postResponse: PostResponse<RedirectUrlResponse> = post(
                "$SERVICE_URL/redirection/url",
                request,
                object : ParameterizedTypeReference<PostResponse<RedirectUrlResponse>>() {})
            RedirectUrlResponse(postResponse.response?.url, postResponse.response?.rawBody)
        } catch (e: Exception) {
            throw PosException(
                java.lang.String.format(
                    "Error occurred when getting payment redirect url for sofort, ReferenceId:%s. Message: %s",
                    request.referenceNumber,
                    e.message
                ), e
            )
        }
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
