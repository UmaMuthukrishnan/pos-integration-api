import { PosCredential } from "../common/pos-credential";
import { Currency } from "../common/currency";

export interface RedirectUrlRequest {
  posCredential: PosCredential;
  amount: number;
  currency: Currency;
  referenceNumber: string;
  countryCode: string;
  callbackUrl: string;
}
