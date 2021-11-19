package client

import (
	"errors"
	"github.com/parnurzeal/gorequest"
)

type RestClient interface {
	Post(url string, request interface{}) (interface{}, error)
}

type Rest struct {
}

func (s *Rest) Post(url string, request interface{}) (response interface{}, err error) {
	_, _, errs := gorequest.New().Post(url).
		Send(request).
		EndStruct(&response)

	if len(errs) != 0 {
		return response, errors.New("Failed to send request ")
	}

	return
}
