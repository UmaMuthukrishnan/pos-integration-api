export interface AdyenRefundResponse {
  pspReference: string;
  response: string;
  errorCode: string;
  message: string;
  rawBody: string;
}
