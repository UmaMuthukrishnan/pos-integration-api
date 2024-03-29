// Code generated by MockGen. DO NOT EDIT.
// Source: service/pos_service.go

// Package mock_service is a generated GoMock package.
package mocks

import (
	auth "casereview-go/model/auth"
	redirect_url "casereview-go/model/redirect-url"
	refund "casereview-go/model/refund"
	reflect "reflect"

	gomock "github.com/golang/mock/gomock"
)

// MockPosService is a mock of PosService interface.
type MockPosService struct {
	ctrl     *gomock.Controller
	recorder *MockPosServiceMockRecorder
}

// MockPosServiceMockRecorder is the mock recorder for MockPosService.
type MockPosServiceMockRecorder struct {
	mock *MockPosService
}

// NewMockPosService creates a new mock instance.
func NewMockPosService(ctrl *gomock.Controller) *MockPosService {
	mock := &MockPosService{ctrl: ctrl}
	mock.recorder = &MockPosServiceMockRecorder{mock}
	return mock
}

// EXPECT returns an object that allows the caller to indicate expected use.
func (m *MockPosService) EXPECT() *MockPosServiceMockRecorder {
	return m.recorder
}

// Auth mocks base method.
func (m *MockPosService) Auth(request auth.AuthRequest) (auth.AuthResponse, error) {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "Auth", request)
	ret0, _ := ret[0].(auth.AuthResponse)
	ret1, _ := ret[1].(error)
	return ret0, ret1
}

// Auth indicates an expected call of Auth.
func (mr *MockPosServiceMockRecorder) Auth(request interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "Auth", reflect.TypeOf((*MockPosService)(nil).Auth), request)
}

// FetchRedirectionUrl mocks base method.
func (m *MockPosService) FetchRedirectionUrl(request redirect_url.RedirectUrlRequest) (redirect_url.RedirectUrlResponse, error) {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "FetchRedirectionUrl", request)
	ret0, _ := ret[0].(redirect_url.RedirectUrlResponse)
	ret1, _ := ret[1].(error)
	return ret0, ret1
}

// FetchRedirectionUrl indicates an expected call of FetchRedirectionUrl.
func (mr *MockPosServiceMockRecorder) FetchRedirectionUrl(request interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "FetchRedirectionUrl", reflect.TypeOf((*MockPosService)(nil).FetchRedirectionUrl), request)
}

// Refund mocks base method.
func (m *MockPosService) Refund(request refund.RefundRequest) (refund.RefundResponse, error) {
	m.ctrl.T.Helper()
	ret := m.ctrl.Call(m, "Refund", request)
	ret0, _ := ret[0].(refund.RefundResponse)
	ret1, _ := ret[1].(error)
	return ret0, ret1
}

// Refund indicates an expected call of Refund.
func (mr *MockPosServiceMockRecorder) Refund(request interface{}) *gomock.Call {
	mr.mock.ctrl.T.Helper()
	return mr.mock.ctrl.RecordCallWithMethodType(mr.mock, "Refund", reflect.TypeOf((*MockPosService)(nil).Refund), request)
}
