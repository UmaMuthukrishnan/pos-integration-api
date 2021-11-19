import { RefundRequest } from "../model/refund/refund-request";
import { AuthRequest } from "../model/auth/auth-request";
import { AuthResponse } from "../model/auth/auth-response";
import { RefundResponse } from "../model/refund/refund-response";
import { RedirectUrlResponse } from "../model/redirect-url/redirect-url-response";
import { RedirectUrlRequest } from "../model/redirect-url/redirect-url-request";

export interface PosService {
  refund(request: RefundRequest): Promise<RefundResponse>;

  auth(request: AuthRequest): Promise<AuthResponse>;

  fetchRedirectionUrl(request: RedirectUrlRequest): Promise<RedirectUrlResponse>;
}
