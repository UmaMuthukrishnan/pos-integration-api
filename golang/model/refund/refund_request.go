package refund

import "casereview-go/model/common"

type RefundRequest struct {
	PosCredential         common.PosCredential
	Amount                float64
	Currency              common.Currency
	RefundReferenceNumber string
}
