package model

type EstAuthResponse struct {
	OrderId        string
	GroupId        string
	Response       string
	AuthCode       string
	HostRefNum     string
	ProcReturnCode string
	TransactionId  string
	ErrMsg         string
	RawBody        string
}

func (e *EstAuthResponse) IsSuccess() bool {
	return e.Response == "Approved"
}
