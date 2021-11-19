package est_test

import (
	"casereview-go/mocks"
	"casereview-go/model/auth"
	"casereview-go/model/refund"
	"casereview-go/service/est"
	"casereview-go/service/est/model"
	"github.com/golang/mock/gomock"
	"github.com/stretchr/testify/assert"
	"testing"
)

func Test_it_should_return_successfully_refund_response(t *testing.T) {
	//given
	controller := gomock.NewController(t)
	client := mocks.NewMockRestClient(controller)
	req := refund.RefundRequest{}
	resp := model.EstRefundResponse{
		Response:  "r",
		ErrorCode: "a",
		Message:   "b",
		RawBody:   "z",
	}

	client.EXPECT().Post("https://www.est.com/pos/api/v1/refund", req).Return(resp, nil)

	s := est.NewService(client)
	//when
	response, err := s.Refund(req)
	//then
	assert.Nil(t, err)
	assert.Equal(t, response.RawResponse, "z")
}

func Test_it_should_return_successfully_auth_response(t *testing.T) {
	//given
	controller := gomock.NewController(t)
	client := mocks.NewMockRestClient(controller)
	req := auth.AuthRequest{}
	resp := model.EstAuthResponse{
		Response: "Approved",
		RawBody:  "z",
	}

	client.EXPECT().Post("https://www.est.com/pos/api/v1/auth", req).Return(resp, nil)

	s := est.NewService(client)
	//when
	response, err := s.Auth(req)
	//then
	assert.Nil(t, err)
	assert.Equal(t, response.RawResponse, "z")
	assert.Equal(t, response.IsSuccess, true)
}
