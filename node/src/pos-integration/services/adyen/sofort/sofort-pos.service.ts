import axios, { AxiosResponse } from "axios";
import { Injectable, MethodNotAllowedException } from "@nestjs/common";
import { PosService } from "../../pos.service";
import { RefundRequest } from "../../../model/refund/refund-request";
import { RefundResponse } from "../../../model/refund/refund-response";
import { AuthRequest } from "../../../model/auth/auth-request";
import { AuthResponse } from "../../../model/auth/auth-response";
import { RedirectUrlRequest } from "../../../model/redirect-url/redirect-url-request";
import { RedirectUrlResponse } from "../../../model/redirect-url/redirect-url-response";
import { PosException } from "../../../exception/pos.exception";
import { AdyenRefundResponse } from "../model/adyen-refund-response";

@Injectable()
export class SofortPosService implements PosService {
  private readonly SERVICE_URL = "https://www.adyen.com/api/v1";

  public async refund(request: RefundRequest): Promise<RefundResponse> {
    try {
      const response = await axios.post<RefundRequest, AxiosResponse<AdyenRefundResponse>>(this.SERVICE_URL + "/refund", request);

      if (!SofortPosService.isSuccessResponse(response.status)) {
        throw new PosException(`Error occurred when refunding transaction, ReferenceId:${request.refundReferenceNumber}. Message: ${response.statusText}`);
      }

      return SofortPosService.mapToRefundResponse(response.data);

    } catch (e) {
      throw new PosException(`Error occurred when refunding transaction, ReferenceId:${request.refundReferenceNumber}. Message: ${e.message}`, e);
    }
  }

  public async auth(request: AuthRequest): Promise<AuthResponse> {
    throw new MethodNotAllowedException("Unsupported auth operation for Adyen Sofort Pos");
  }

  public async fetchRedirectionUrl(request: RedirectUrlRequest): Promise<RedirectUrlResponse> {
    try {
      const response = await axios.post<RedirectUrlRequest, AxiosResponse<RedirectUrlResponse>>(this.SERVICE_URL + "/redirection/url", request);

      if (!SofortPosService.isSuccessResponse(response.status)) {
        throw new PosException(`Error occurred when getting payment redirect url for sofort, ReferenceId: ${request.referenceNumber}. Message: ${response.statusText}`);
      }

      return {
        url: response.data.url,
        rawBody: response.data.rawBody
      };

    } catch (e) {
      throw new PosException(`Error occurred when getting payment redirect url for sofort, ReferenceId: ${request.referenceNumber}. Message: ${e.message}`, e);
    }
  }

  private static isSuccessResponse(status: number): boolean {
    return status >= 200 && status < 300;
  }

  private static mapToRefundResponse(response: AdyenRefundResponse): RefundResponse {
    return {
      isSuccess: response.response === "[refund-received]",
      resultCode: response.errorCode,
      resultMessage: response.message,
      bankReferenceNumber: null,
      rawResponse: response.rawBody
    };
  }
}
