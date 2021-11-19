import { PosCredentialDto } from "./pos-credential-dto";
import { ApiModelProperty } from "@nestjs/swagger/dist/decorators/api-model-property.decorator";

export class RedirectUrlRequestDto {
  @ApiModelProperty({ description: 'posCredential', type: PosCredentialDto })
  public posCredential: PosCredentialDto;

  @ApiModelProperty({ description: 'amount' })
  public amount: number;

  @ApiModelProperty({ description: 'referenceNumber' })
  public referenceNumber: string;

  @ApiModelProperty({ description: 'countryCode' })
  public countryCode: string;

  @ApiModelProperty({ description: 'callbackUrl' })
  public callbackUrl: string;
}
