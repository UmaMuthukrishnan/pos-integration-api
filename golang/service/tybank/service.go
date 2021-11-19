package tybank

import (
	"casereview-go/model/auth"
	redirect_url "casereview-go/model/redirect-url"
	"casereview-go/model/refund"
	"casereview-go/service"
)

type tybankPosService struct {
}

func NewService() service.PosService {
	return &tybankPosService{}
}

func (t *tybankPosService) Refund(request refund.RefundRequest) (refund.RefundResponse, error) {
	// TODO: business implementation section
	// "refund" flow provides to refund the amount to user via TyBank virtual pos.
	// The user will see refunded amount in his or her account in 1-2 days.
	return refund.RefundResponse{}, nil
}

func (t *tybankPosService) Auth(request auth.AuthRequest) (auth.AuthResponse, error) {
	// TODO: business implementation section
	// "auth" flow enables the user to make payment via credit card from TyBank virtual pos.
	// This payment method captures the amount from the user's credit card directly.
	return auth.AuthResponse{}, nil
}

func (t *tybankPosService) FetchRedirectionUrl(request redirect_url.RedirectUrlRequest) (redirect_url.RedirectUrlResponse, error) {
	return redirect_url.RedirectUrlResponse{}, nil
}
