using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using PosIntegration.Model.Auth;
using PosIntegration.Model.RedirectUrl;
using PosIntegration.Model.Refund;
using PosIntegration.Service.Adyen.Sofort;
using PosIntegration.Service.Est;

namespace PosIntegration.Api.Controllers
{
    [ApiController]
    [Route("makePayment")]
    public class PosIntegrationController : ControllerBase
    {
        private readonly SofortPosService _sofortPosService;
        private readonly EstPosService _estPosService;

        public PosIntegrationController(SofortPosService sofortPosService, EstPosService estPosService)
        {
            _sofortPosService = sofortPosService;
            _estPosService = estPosService;
        }

        [HttpPost]
        [Route("getRedirectionUrlWithAdyenSofort")]
        public async Task<IActionResult> FetchRedirectionUrlWithAdyenSofort([FromBody] RedirectUrlRequest request)
        {
            return Ok(await _sofortPosService.FetchRedirectionUrlAsync(request));
        }

        [HttpPost]
        [Route("refundWithAdyenSofort")]
        public async Task<IActionResult> RefundWithAdyenSofort([FromBody] RefundRequest request)
        {
            return Ok(await _sofortPosService.RefundAsync(request));
        }

        [HttpPost]
        [Route("authWithEst")]
        public async Task<IActionResult> AuthWithEst([FromBody] AuthRequest request)
        {
            return Ok(await _estPosService.AuthAsync(request));
        }

        [HttpPost]
        [Route("refundWithEst")]
        public async Task<IActionResult> RefundWithEst([FromBody] RefundRequest request)
        {
            return Ok(await _estPosService.RefundAsync(request));
        }
    }
}