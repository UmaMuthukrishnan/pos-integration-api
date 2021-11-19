package auth

type AuthResponse struct {
	IsSuccess           bool
	ResultCode          string
	ResultMessage       string
	TransactionId       string
	RawResponse         string
	AuthCode            string
	HostReferenceNumber string
}
