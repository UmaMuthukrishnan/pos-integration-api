#### WELCOME TO TRENDYOL HIRINGDAY

# Virtual Pos Integration Project

### Description:

The Virtual Pos Integration project enables you to integrate with payment providers or banks and make payment and post-payment transactions

#### Integrations include the following functions;

* **auth**: Authorizes to charge the customer a fee.
* **refund**: efunds a successful payment to the customer.
* **fetchRedirectionUrl**: Ensures customer receives the URL which redirects to the payment provider’s page. This URL redirects customer to the payment provider’s page. It makes the payment on this page.

Above-given transactions are integrated individually for each payment provider in the project. Individual integration is performed for each payment provider or bank. To be able to use integrations, a REST layer is formed. Integrations with payment providers such as “Sofort”, “Est” are completed in the given project.

#### Payment Providers and Banks in the Project;

* **Adyen**: This payment provider makes the transactions related to the overseas payments. It offers to payment methods: Sofort.

* **Est**: This payment provider makes the transactions related to the payments via card transactions.

* **TyBank**: This bank makes the transactions related to the payments via card transactions.

#### Following tasks are already completed in the project;

* “Auth”, “refund” functions belonging to Est payment provider are integrated. Est provider does not support “fetchRedirectionUrl” function.
* “Refund” and “fetchRedirectionUrl” functions are integrated for “Sofort” payment method under Adyen payment provider. “Sofort” payment method does not support “auth” function.
* Request/Response classes are prepared for completed integrations.
* REST layer is prepared to use virtual pos integrations.
* Unit tests are written for completed integrations.

#### What to do:

1 - You are responsible to implement TyBankPosService as explained below on the 1.1 and 1.2;
- 1.1 - The “refund” function in TyBank’s “TyBankPosService” class must be completed by you. For the “refund” transaction, fields in the “RefundRequest” data model are filled in. An HTTP POST request should be sent to “https://www.tybank.com/pos/api/v1/tybank/refund”. Model the response with “TyBankRefundResponse”. Fields in the class are as follows: “response”, “errorCode”, “message”.
- 1.2 - The “auth” function in TyBank’s “TyBankPosService” class must be completed by you. For the “auth” transaction, fields in the “AuthRequest” data model are filled in. An HTTP POST request should be sent to “https://www.tybank.com/pos/api/v1/tybank/auth”. Model the response with “TyBankAuthResponse”. Fields in the class are as follows: “response”, “authCode”, “transactionId”, “errMsg”.

2 - You are expected to take any and all refactoring actions for SOLID, Clean Code, OOP principles, including pre-written codes in the project.

3 - You need to write unit tests for upcoming integrations in the project.