package service

import (
	"casereview-go/model/auth"
	redirect_url "casereview-go/model/redirect-url"
	"casereview-go/model/refund"
)

type PosService interface {
	Refund(request refund.RefundRequest) (refund.RefundResponse, error)
	Auth(request auth.AuthRequest) (auth.AuthResponse, error)
	FetchRedirectionUrl(request redirect_url.RedirectUrlRequest) (redirect_url.RedirectUrlResponse, error)
}
