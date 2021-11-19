package main

import (
	"casereview-go/controller"
	"casereview-go/service/adyen/sofort"
	"casereview-go/service/client"
	"casereview-go/service/est"
	"fmt"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.New()

	ctr := controller.NewController(sofort.NewService(&client.Rest{}), est.NewService(&client.Rest{}))
	r.POST("/getRedirectionUrlWithAdyenSofort", ctr.FetchRedirectionUrlWithAdyenSofort)
	r.POST("/refundWithAdyenSofort", ctr.RefundWithAdyenSofort)
	r.POST("/authWithEst", ctr.AuthWithEst)
	r.POST("/refundWithEst", ctr.RefundWithEst)

	if err := r.Run(":8080"); err != nil {
		fmt.Println("Application crashed")
	}
}
