package redirect_url

import "casereview-go/model/common"

type RedirectUrlRequest struct {
	PosCredential   common.PosCredential
	Amount          float64
	Currency        common.Currency
	ReferenceNumber string
	CountryCode     string
	CallbackUrl     string
}
