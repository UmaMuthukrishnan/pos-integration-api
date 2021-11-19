namespace PosIntegration.Service.Adyen.Model
{
    public class AdyenRefundResponse
    {
        public string PspReference { get; set; }
        public string Response { get; set; }
        public string ErrorCode { get; set; }
        public string Message { get; set; }
        public string RawBody { get; set; }

        public bool IsSuccess()
        {
            return "[refund-received]".Equals(Response);
        }
    }
}