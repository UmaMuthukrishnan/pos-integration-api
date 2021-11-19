import { ApiModelProperty } from "@nestjs/swagger/dist/decorators/api-model-property.decorator";

export class RefundResponseDto {
  @ApiModelProperty({ description: 'isSuccess' })
  public isSuccess: boolean;

  @ApiModelProperty({ description: 'resultCode' })
  public resultCode: string;

  @ApiModelProperty({ description: 'resultMessage' })
  public resultMessage: string;

  @ApiModelProperty({ description: 'bankReferenceNumber' })
  public bankReferenceNumber: string;

  @ApiModelProperty({ description: 'rawResponse' })
  public rawResponse: string;
}
