import { PosCredentialDto } from "./pos-credential-dto";
import { ApiModelProperty } from "@nestjs/swagger/dist/decorators/api-model-property.decorator";

export class AuthRequestDto {
  @ApiModelProperty({ description: 'referenceNumber' })
  public referenceNumber: string;

  @ApiModelProperty({ description: 'posCredential', type: PosCredentialDto })
  public posCredential: PosCredentialDto;

  @ApiModelProperty({ description: 'amount' })
  public amount: number;

  @ApiModelProperty({ description: 'cardNumber' })
  public cardNumber: string;

  @ApiModelProperty({ description: 'expireMonth' })
  public expireMonth: number;

  @ApiModelProperty({ description: 'expireYear' })
  public expireYear: number;

  @ApiModelProperty({ description: 'cvv' })
  public cvv: number;
}
