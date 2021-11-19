import { ApiModelProperty } from "@nestjs/swagger/dist/decorators/api-model-property.decorator";

export class AuthResponseDto {
  @ApiModelProperty({ description: 'isSuccess' })
  public isSuccess: boolean;

  @ApiModelProperty({ description: 'resultCode' })
  public resultCode: string;

  @ApiModelProperty({ description: 'resultMessage' })
  public resultMessage: string;

  @ApiModelProperty({ description: 'transactionId' })
  public transactionId: string;

  @ApiModelProperty({ description: 'rawResponse' })
  public rawResponse: string;

  @ApiModelProperty({ description: 'authCode' })
  public authCode: string;

  @ApiModelProperty({ description: 'hostReferenceNumber' })
  public hostReferenceNumber: string;
}
