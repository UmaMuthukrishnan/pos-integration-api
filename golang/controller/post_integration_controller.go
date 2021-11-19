package controller

import (
	"casereview-go/model/auth"
	redirect_url "casereview-go/model/redirect-url"
	"casereview-go/model/refund"
	"casereview-go/service"
	"github.com/gin-gonic/gin"
	"net/http"
)

type controller struct {
	sofortPos service.PosService
	esPos     service.PosService
}

func NewController(sofortPos, esPos service.PosService) *controller {
	return &controller{
		sofortPos: sofortPos,
		esPos:     esPos,
	}
}

func (c *controller) FetchRedirectionUrlWithAdyenSofort(ctx *gin.Context) {
	req := redirect_url.RedirectUrlRequest{}
	if err := ctx.Bind(req); err != nil {
		ctx.JSON(http.StatusBadRequest, err)
		return
	}
	url, err := c.sofortPos.FetchRedirectionUrl(req)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, err)
		return
	}

	ctx.JSON(http.StatusOK, url)
}

func (c *controller) RefundWithAdyenSofort(ctx *gin.Context) {
	req := refund.RefundRequest{}
	if err := ctx.Bind(req); err != nil {
		ctx.JSON(http.StatusBadRequest, err)
		return
	}
	url, err := c.sofortPos.Refund(req)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, err)
		return
	}

	ctx.JSON(http.StatusOK, url)
}

func (c *controller) AuthWithEst(ctx *gin.Context) {
	req := auth.AuthRequest{}
	if err := ctx.Bind(req); err != nil {
		ctx.JSON(http.StatusBadRequest, err)
		return
	}
	url, err := c.esPos.Auth(req)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, err)
		return
	}

	ctx.JSON(http.StatusOK, url)
}

func (c *controller) RefundWithEst(ctx *gin.Context) {
	req := refund.RefundRequest{}
	if err := ctx.Bind(req); err != nil {
		ctx.JSON(http.StatusBadRequest, err)
		return
	}
	url, err := c.esPos.Refund(req)
	if err != nil {
		ctx.JSON(http.StatusInternalServerError, err)
		return
	}

	ctx.JSON(http.StatusOK, url)
}
