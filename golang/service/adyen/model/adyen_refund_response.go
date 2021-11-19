package model

type AdyenRefundResponse struct {
	PspReference string
	Response     string
	ErrorCode    string
	Message      string
	RawBody      string
}

func (a *AdyenRefundResponse) IsSuccess() bool {
	return a.Response == "[refund-received]"
}
