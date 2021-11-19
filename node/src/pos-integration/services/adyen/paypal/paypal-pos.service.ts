import { Injectable } from "@nestjs/common";
import { PosService } from "../../pos.service";
import { RefundResponse } from "../../../model/refund/refund-response";
import { RefundRequest } from "../../../model/refund/refund-request";
import { AuthRequest } from "../../../model/auth/auth-request";
import { AuthResponse } from "../../../model/auth/auth-response";
import { RedirectUrlRequest } from "../../../model/redirect-url/redirect-url-request";
import { RedirectUrlResponse } from "../../../model/redirect-url/redirect-url-response";

@Injectable()
export class PaypalPosService implements PosService {
  public async refund(request: RefundRequest): Promise<RefundResponse> {
    return null;
  }

  public async auth(request: AuthRequest): Promise<AuthResponse> {
    return null;
  }

  public async fetchRedirectionUrl(request: RedirectUrlRequest): Promise<RedirectUrlResponse> {
    // TODO: business implementation section
    // "fetchRedirectionUrl" flow enables the user to make payment via paypal payment provider.
    // This payment method gives a paypal payment url which named as 'redirection url'.
    return null;
  }
}
