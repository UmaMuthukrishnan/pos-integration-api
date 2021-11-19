import { Body, Controller, HttpCode, HttpStatus, Post } from "@nestjs/common";
import { SofortPosService } from "../services/adyen/sofort/sofort-pos.service";
import { EstPosService } from "../services/est/est-pos.service";
import { RedirectUrlRequest } from "../model/redirect-url/redirect-url-request";
import { RefundRequest } from "../model/refund/refund-request";
import { AuthRequest } from "../model/auth/auth-request";
import { MapperService } from "../services/mapper.service";
import { RedirectUrlRequestDto } from "./models/redirect-url-request-dto";
import { RefundRequestDto } from "./models/refund-request-dto";
import { AuthRequestDto } from "./models/auth-request-dto";
import { ApiOperation, ApiResponse } from "@nestjs/swagger";
import { RefundResponseDto } from "./models/refund-response-dto";
import { RedirectUrlResponseDto } from "./models/redirect-url-response-dto";
import { AuthResponseDto } from "./models/auth-response-dto";

@Controller("/makePayment")
export class PosIntegrationController {
  private readonly estPosService: EstPosService;
  private readonly sofortPosService: SofortPosService;
  private readonly mapperService: MapperService;

  public constructor(
    sofortPosService: SofortPosService,
    estPosService: EstPosService,
    mapperService: MapperService) {

    this.estPosService = estPosService;
    this.sofortPosService = sofortPosService;
    this.mapperService = mapperService;
  }

  @Post("/getRedirectionUrlWithAdyenSofort")
  @HttpCode(HttpStatus.ACCEPTED)
  @ApiOperation({ description: 'Fetch Redirection Url With Adyen Sofort' })
  @ApiResponse({ status: HttpStatus.ACCEPTED, type: RedirectUrlResponseDto })
  public async fetchRedirectionUrlWithAdyenSofort(@Body() body: RedirectUrlRequestDto): Promise<RedirectUrlResponseDto> {
    const request = this.mapperService.mapToDomain<RedirectUrlRequestDto, RedirectUrlRequest>(body);
    const response = await this.sofortPosService.fetchRedirectionUrl(request);
    return this.mapperService.mapToDto(response, RedirectUrlResponseDto);
  }

  @Post("/refundWithAdyenSofort")
  @HttpCode(HttpStatus.ACCEPTED)
  @ApiOperation({ description: 'Refund With With Adyen Sofort' })
  @ApiResponse({ status: HttpStatus.ACCEPTED, type: RefundResponseDto })
  public async refundWithAdyenSofort(@Body() body: RefundRequestDto): Promise<RefundResponseDto> {
    const request = this.mapperService.mapToDomain<RefundRequestDto, RefundRequest>(body);
    const response = await this.sofortPosService.refund(request);
    return this.mapperService.mapToDto(response, RefundResponseDto);
  }

  @Post("/authWithEst")
  @HttpCode(HttpStatus.ACCEPTED)
  @ApiOperation({ description: 'Auth With Est' })
  @ApiResponse({ status: HttpStatus.ACCEPTED, type: AuthResponseDto })
  public async authWithEst(@Body() body: AuthRequestDto): Promise<AuthResponseDto> {
    const request = this.mapperService.mapToDomain<AuthRequestDto, AuthRequest>(body);
    const response = await this.estPosService.auth(request);
    return this.mapperService.mapToDto(response, AuthResponseDto);
  }

  @Post("/refundWithEst")
  @HttpCode(HttpStatus.ACCEPTED)
  @ApiOperation({ description: 'Refund With Est' })
  @ApiResponse({ status: HttpStatus.ACCEPTED, type: RefundResponseDto })
  public async refundWithEst(@Body() body: RefundRequestDto): Promise<RefundResponseDto> {
    const request = this.mapperService.mapToDomain<RefundRequestDto, RefundRequest>(body);
    const response = await this.estPosService.refund(request);
    return this.mapperService.mapToDto(response, RefundResponseDto);
  }
}
