package model

type EstRefundResponse struct {
	Response  string
	ErrorCode string
	Message   string
	RawBody   string
}

func (e *EstRefundResponse) IsSuccess() bool {
	return e.Response == "[refund-ok]"
}
