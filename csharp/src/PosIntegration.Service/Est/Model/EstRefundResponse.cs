namespace PosIntegration.Service.Est.Model
{
    public class EstRefundResponse
    {
        public string Response { get; set; }
        public string ErrorCode { get; set; }
        public string Message { get; set; }
        public string RawBody { get; set; }

        public bool IsSuccess()
        {
            return "[refund-ok]".Equals(Response);
        }
    }
}