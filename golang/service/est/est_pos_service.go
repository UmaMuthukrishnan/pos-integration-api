package est

import (
	"casereview-go/model/auth"
	redirect_url "casereview-go/model/redirect-url"
	"casereview-go/model/refund"
	"casereview-go/service"
	"casereview-go/service/client"
	"casereview-go/service/est/model"
	"errors"
)

type estPosService struct {
	client.RestClient
}

func NewService(restClient client.RestClient) service.PosService {
	return &estPosService{
		restClient,
	}
}

func (s *estPosService) Refund(request refund.RefundRequest) (response refund.RefundResponse, err error) {
	res, err := s.Post("https://www.est.com/pos/api/v1/refund", request)
	if err != nil {
		return refund.RefundResponse{}, err
	}

	result := res.(model.EstRefundResponse)

	response.RawResponse = result.RawBody
	response.IsSuccess = result.IsSuccess()
	response.BankReferenceNumber = ""
	response.ResultCode = result.ErrorCode
	response.ResultMessage = result.Message
	return
}

func (s *estPosService) Auth(request auth.AuthRequest) (response auth.AuthResponse, err error) {
	res, err := s.Post("https://www.est.com/pos/api/v1/auth", request)
	if err != nil {
		return response, err
	}

	result := res.(model.EstAuthResponse)
	response.IsSuccess = result.IsSuccess()
	response.ResultCode = result.ProcReturnCode
	response.ResultMessage = result.ErrMsg
	response.TransactionId = result.TransactionId
	response.RawResponse = result.RawBody
	response.AuthCode = result.AuthCode
	response.HostReferenceNumber = result.HostRefNum
	return
}

func (s *estPosService) FetchRedirectionUrl(request redirect_url.RedirectUrlRequest) (response redirect_url.RedirectUrlResponse, err error) {
	return response, errors.New("Unsupported auth operation for Est Pos ")
}
