import { Injectable } from "@nestjs/common";
import { PosService } from "../pos.service";
import { RefundRequest } from "../../model/refund/refund-request";
import { RefundResponse } from "../../model/refund/refund-response";
import { AuthRequest } from "../../model/auth/auth-request";
import { RedirectUrlRequest } from "../../model/redirect-url/redirect-url-request";
import { RedirectUrlResponse } from "../../model/redirect-url/redirect-url-response";
import { AuthResponse } from "../../model/auth/auth-response";

@Injectable()
export class TyBankPosService implements PosService {

  public async refund(request: RefundRequest): Promise<RefundResponse> {
    // TODO: business implementation section
    // "refund" flow provides to refund the amount to user via TyBank virtual pos.
    // The user will see refunded amount in his or her account in 1-2 days.
    return null;
  }

  public async auth(request: AuthRequest): Promise<AuthResponse> {
    // TODO: business implementation section
    // "auth" flow enables the user to make payment via credit card from TyBank virtual pos.
    // This payment method captures the amount from the user's credit card directly.
    return null;
  }

  public async fetchRedirectionUrl(request: RedirectUrlRequest): Promise<RedirectUrlResponse> {
    return null;
  }
}
