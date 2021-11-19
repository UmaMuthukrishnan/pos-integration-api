package auth

import "casereview-go/model/common"

type AuthRequest struct {
	ReferenceNumber string
	PosCredential   common.PosCredential
	Amount          float64
	Currency        common.Currency
	CardNumber      string
	ExpireMonth     int
	ExpireYear      int
	Cvv             string
}

