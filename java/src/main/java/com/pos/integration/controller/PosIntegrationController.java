package com.pos.integration.controller;

import com.pos.integration.exception.PosException;
import com.pos.integration.model.auth.AuthRequest;
import com.pos.integration.model.auth.AuthResponse;
import com.pos.integration.model.redirect_url.RedirectUrlRequest;
import com.pos.integration.model.redirect_url.RedirectUrlResponse;
import com.pos.integration.model.refund.RefundRequest;
import com.pos.integration.model.refund.RefundResponse;
import com.pos.integration.service.adyen.sofort.SofortPosService;
import com.pos.integration.service.est.EstPosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/makePayment")
public class PosIntegrationController {

    @Autowired
    private SofortPosService sofortPosService;

    @Autowired
    private EstPosService estPosService;


    @PostMapping("/getRedirectionUrlWithAdyenSofort")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<RedirectUrlResponse> fetchRedirectionUrlWithAdyenSofort(@RequestBody RedirectUrlRequest request) throws PosException {
        return ResponseEntity.ok(sofortPosService.fetchRedirectionUrl(request));
    }

    @PostMapping("/refundWithAdyenSofort")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<RefundResponse> refundWithAdyenSofort(@RequestBody RefundRequest request) throws PosException {
        return ResponseEntity.ok(sofortPosService.refund(request));
    }

    @PostMapping("/authWithEst")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthResponse> authWithEst(@RequestBody AuthRequest request) throws PosException {
        return ResponseEntity.ok(estPosService.auth(request));
    }

    @PostMapping("/refundWithEst")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<RefundResponse> refundWithEst(@RequestBody RefundRequest request) throws PosException {
        return ResponseEntity.ok(estPosService.refund(request));
    }
}
