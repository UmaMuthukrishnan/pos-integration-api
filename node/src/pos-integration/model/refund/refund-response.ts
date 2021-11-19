export interface RefundResponse {
  isSuccess: boolean;
  resultCode: string;
  resultMessage: string;
  bankReferenceNumber: string;
  rawResponse: string;
}
