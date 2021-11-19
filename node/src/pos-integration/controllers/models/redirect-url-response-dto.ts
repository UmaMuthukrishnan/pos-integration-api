import { ApiModelProperty } from "@nestjs/swagger/dist/decorators/api-model-property.decorator";

export class RedirectUrlResponseDto {
  @ApiModelProperty({ description: 'url' })
  public url: string;

  @ApiModelProperty({ description: 'rawBody' })
  public rawBody: string;
}
