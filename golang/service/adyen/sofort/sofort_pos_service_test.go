package sofort_test

import (
	"casereview-go/mocks"
	redirect_url "casereview-go/model/redirect-url"
	"casereview-go/model/refund"
	"casereview-go/service/adyen/model"
	"casereview-go/service/adyen/sofort"
	"github.com/golang/mock/gomock"
	"github.com/stretchr/testify/assert"
	"testing"
)

func Test_it_should_return_successfully_refund_response(t *testing.T) {
	//given
	controller := gomock.NewController(t)
	client := mocks.NewMockRestClient(controller)
	req := refund.RefundRequest{}
	resp := model.AdyenRefundResponse{
		PspReference: "t",
		Response:     "[refund-received]",
		ErrorCode:    "a",
		Message:      "b",
		RawBody:      "z",
	}

	client.EXPECT().Post("https://www.adyen.com/api/v1/refund", req).Return(resp, nil)

	s := sofort.NewService(client)
	//when
	response, err := s.Refund(req)
	//then
	assert.Nil(t, err)
	assert.Equal(t, response.RawResponse, "z")
	assert.Equal(t, response.IsSuccess, true)
}

func Test_it_should_return_successfully_redirection_url(t *testing.T) {
	//given
	controller := gomock.NewController(t)
	client := mocks.NewMockRestClient(controller)
	req := redirect_url.RedirectUrlRequest{}
	resp := redirect_url.RedirectUrlResponse{
		Url:     "test",
		RawBody: "body",
	}
	client.EXPECT().Post("https://www.adyen.com/api/v1/redirection/url", req).Return(resp, nil)

	s := sofort.NewService(client)
	//when
	response, err := s.FetchRedirectionUrl(req)
	//then
	assert.Nil(t, err)
	assert.Equal(t, response.Url, "test")
	assert.Equal(t, response.RawBody, "body")
}
