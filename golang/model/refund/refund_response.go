package refund

type RefundResponse struct {
	IsSuccess           bool
	ResultCode          string
	ResultMessage       string
	BankReferenceNumber string
	RawResponse         string
}
