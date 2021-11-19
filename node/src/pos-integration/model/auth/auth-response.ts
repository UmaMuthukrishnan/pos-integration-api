export interface AuthResponse {
  isSuccess: boolean;
  resultCode: string;
  resultMessage: string;
  transactionId: string;
  rawResponse: string;
  authCode: string;
  hostReferenceNumber: string;
}
