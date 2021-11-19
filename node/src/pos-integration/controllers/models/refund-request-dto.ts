import { PosCredentialDto } from "./pos-credential-dto";
import { ApiModelProperty } from "@nestjs/swagger/dist/decorators/api-model-property.decorator";

export class RefundRequestDto {

  @ApiModelProperty({ description: 'posCredential', type: PosCredentialDto })
  public posCredential: PosCredentialDto;

  @ApiModelProperty({ description: 'amount' })
  public amount: number;

  @ApiModelProperty({ description: 'refundReferenceNumber' })
  public refundReferenceNumber: string;
}
