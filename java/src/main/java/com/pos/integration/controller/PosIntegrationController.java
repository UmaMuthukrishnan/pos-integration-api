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
import com.pos.integration.service.tybank.TyBankPosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@RequestMapping("/makePayment")
@RequiredArgsConstructor
public class PosIntegrationController {

    private final SofortPosService sofortPosService;

    private final EstPosService estPosService;

    private final TyBankPosService tyBankPosService;


    @PostMapping("/getRedirectionUrlWithAdyenSofort")
    @ResponseStatus(ACCEPTED)
    public ResponseEntity<RedirectUrlResponse> fetchRedirectionUrlWithAdyenSofort(@RequestBody RedirectUrlRequest request) throws PosException {
        return ResponseEntity.ok(sofortPosService.fetchRedirectionUrl(request));
    }

    @PostMapping(value = "/refundWithAdyenSofort",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(ACCEPTED)
    public ResponseEntity<RefundResponse> refundWithAdyenSofort(@RequestBody RefundRequest request) throws PosException {
        return ResponseEntity.ok(sofortPosService.refund(request));
    }

    @PostMapping("/authWithEst")
    @ResponseStatus(ACCEPTED)
    public ResponseEntity<AuthResponse> authWithEst(@RequestBody AuthRequest request) throws PosException {
        return ResponseEntity.ok(estPosService.auth(request));
    }

    @PostMapping("/refundWithEst")
    @ResponseStatus(ACCEPTED)
    public ResponseEntity<RefundResponse> refundWithEst(@RequestBody RefundRequest request) throws PosException {
        return ResponseEntity.ok(estPosService.refund(request));
    }

    @PostMapping("/refundWithTyBank")
    @ResponseStatus(ACCEPTED)
    public ResponseEntity<RefundResponse> refundWithTyBank( @RequestBody RefundRequest request) throws  PosException {
        return ResponseEntity.ok(tyBankPosService.refund(request));
    }

    @PostMapping("/authWithTyBank")
    @ResponseStatus(ACCEPTED)
    public ResponseEntity<AuthResponse> authWithTyBank(@RequestBody AuthRequest request) throws PosException {
        return ResponseEntity.ok(tyBankPosService.auth(request));
    }
}
