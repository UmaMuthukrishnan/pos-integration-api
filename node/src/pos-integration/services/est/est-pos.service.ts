import { PosService } from "../pos.service";
import { RefundResponse } from "../../model/refund/refund-response";
import { RefundRequest } from "../../model/refund/refund-request";
import { AuthRequest } from "../../model/auth/auth-request";
import { AuthResponse } from "../../model/auth/auth-response";
import { RedirectUrlRequest } from "../../model/redirect-url/redirect-url-request";
import { RedirectUrlResponse } from "../../model/redirect-url/redirect-url-response";
import { Injectable, MethodNotAllowedException } from "@nestjs/common";
import axios, { AxiosResponse } from "axios";
import { EstRefundResponse } from "./model/est-refund-response";
import { PosException } from "../../exception/pos.exception";
import { EstAuthResponse } from "./model/est-auth-response";

@Injectable()
export class EstPosService implements PosService {

  public async refund(request: RefundRequest): Promise<RefundResponse> {
    try {
      const response = await axios.post<RefundRequest, AxiosResponse<EstRefundResponse>>("https://www.est.com/pos/api/v1/refund", request);

      if (!EstPosService.isSuccessResponse(response.status)) {
        throw new PosException(`Error occurred when refunding transaction, ReferenceId:${request.refundReferenceNumber}. Message: ${response.statusText}`);
      }

      return EstPosService.mapToRefundResponse(response.data);

    } catch (e) {
      throw new PosException(`Error occurred when refunding transaction, ReferenceId:${request.refundReferenceNumber}. Message: ${e.message}`, e);
    }
  }

  public async auth(request: AuthRequest): Promise<AuthResponse> {
    try {
      const response = await axios.post<AuthRequest, AxiosResponse<EstAuthResponse>>("https://www.est.com/pos/api/v1/auth", request);

      if (!EstPosService.isSuccessResponse(response.status)) {
        throw new PosException(`Error occurred when selling transaction, ReferenceId: ${request.referenceNumber}. Message: ${response.statusText}`);
      }

      return EstPosService.mapToAuthResponse(response.data);

    } catch (e) {
      throw new PosException(`Error occurred when selling transaction, ReferenceId: ${request.referenceNumber}. Message: ${e.message}`, e);
    }
  }

  public async fetchRedirectionUrl(request: RedirectUrlRequest): Promise<RedirectUrlResponse> {
    throw new MethodNotAllowedException("Unsupported auth operation for Est Pos");
  }

  private static isSuccessResponse(status: number): boolean {
    return status >= 200 && status < 300;
  }

  private static mapToRefundResponse(response: EstRefundResponse): RefundResponse {
    return {
      isSuccess: response.response === "[refund-ok]",
      resultCode: response.errorCode,
      resultMessage: response.message,
      bankReferenceNumber: null,
      rawResponse: response.rawBody
    };
  }

  private static mapToAuthResponse(response: EstAuthResponse): AuthResponse {
    return {
      isSuccess: response.response === "Approved",
      resultCode: response.procReturnCode,
      resultMessage: response.errMsg,
      transactionId: response.transactionId,
      rawResponse: response.rawBody,
      authCode: response.authCode,
      hostReferenceNumber: response.hostRefNum
    };
  }
}
