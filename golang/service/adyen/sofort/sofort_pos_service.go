package sofort

import (
	"casereview-go/model/auth"
	redirect_url "casereview-go/model/redirect-url"
	"casereview-go/model/refund"
	"casereview-go/service"
	"casereview-go/service/adyen/model"
	"casereview-go/service/client"
	"errors"
)

const ServiceUrl = "https://www.adyen.com/api/v1"

type sofortPosService struct {
	client.RestClient
}

func NewService(restClient client.RestClient) service.PosService {
	return &sofortPosService{
		restClient,
	}
}

func (s *sofortPosService) Refund(request refund.RefundRequest) (response refund.RefundResponse, err error) {
	refundUrl := ServiceUrl + "/refund"
	res, err := s.Post(refundUrl, request)
	if err != nil {
		return refund.RefundResponse{}, err
	}

	result := res.(model.AdyenRefundResponse)

	response.RawResponse = result.RawBody
	response.IsSuccess = result.IsSuccess()
	response.BankReferenceNumber = ""
	response.ResultCode = result.ErrorCode
	response.ResultMessage = result.Message
	return
}

func (s *sofortPosService) Auth(request auth.AuthRequest) (response auth.AuthResponse, err error) {
	return auth.AuthResponse{}, errors.New("Unsupported auth operation for Adyen Sofort Pos ")
}

func (s *sofortPosService) FetchRedirectionUrl(request redirect_url.RedirectUrlRequest) (response redirect_url.RedirectUrlResponse, err error) {
	redirectUrl := ServiceUrl + "/redirection/url"
	result, err := s.Post(redirectUrl, request)
	if err != nil {
		return redirect_url.RedirectUrlResponse{}, err
	}
	return result.(redirect_url.RedirectUrlResponse), nil
}
