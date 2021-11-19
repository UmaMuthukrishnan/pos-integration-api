using System.Threading.Tasks;
using PosIntegration.Model.Auth;
using PosIntegration.Model.RedirectUrl;
using PosIntegration.Model.Refund;

namespace PosIntegration.Service.Adyen.Paypal
{
    public class PaypalPosService : IPosService
    {
        public Task<RefundResponse> RefundAsync(RefundRequest request)
        {
            return null;
        }

        public Task<AuthResponse> AuthAsync(AuthRequest request)
        {
            return null;
        }

        public Task<RedirectUrlResponse> FetchRedirectionUrlAsync(RedirectUrlRequest request)
        {
            // TODO: business implementation section
            // "FetchRedirectionUrl" flow enables the user to make payment via paypal payment provider.
            // This payment method gives a paypal payment url which named as 'redirection url'.
            return null;
        }
    }
}