package com.trendyol.posintegrationapi.controller

import com.trendyol.posintegrationapi.model.auth.AuthRequest
import com.trendyol.posintegrationapi.model.redirect_url.RedirectUrlRequest
import com.trendyol.posintegrationapi.model.refund.RefundRequest
import com.trendyol.posintegrationapi.service.adyen.sofort.SofortPosService
import com.trendyol.posintegrationapi.service.est.EstPosService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/makePayment")
class PosIntegrationController {
    @Autowired
    private lateinit var sofortPosService: SofortPosService

    @Autowired
    private lateinit var estPosService: EstPosService

    @PostMapping("/getRedirectionUrlWithAdyenSofort")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun fetchRedirectionUrlWithAdyenSofort(@RequestBody request: RedirectUrlRequest) =
        ResponseEntity.ok(sofortPosService.fetchRedirectionUrl(request))

    @PostMapping("/refundWithAdyenSofort")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun refundWithAdyenSofort(@RequestBody request: RefundRequest) = ResponseEntity.ok(sofortPosService.refund(request))

    @PostMapping("/authWithEst")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun authWithEst(@RequestBody request: AuthRequest) = ResponseEntity.ok(estPosService.auth(request))

    @PostMapping("/refundWithEst")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun refundWithEst(@RequestBody request: RefundRequest) = ResponseEntity.ok(estPosService.refund(request))
}