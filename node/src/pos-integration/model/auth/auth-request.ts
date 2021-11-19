import { PosCredential } from "../common/pos-credential";
import { Currency } from "../common/currency";

export interface AuthRequest {
  referenceNumber: string;
  posCredential: PosCredential;
  amount: number;
  currency: Currency;
  cardNumber: string;
  expireMonth: number;
  expireYear: number;
  cvv: number;
}

