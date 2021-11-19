import { PosCredential } from "../common/pos-credential";
import { Currency } from "../common/currency";

export interface RefundRequest {
  posCredential: PosCredential;
  amount: number;
  currency: Currency;
  refundReferenceNumber: string;
}
