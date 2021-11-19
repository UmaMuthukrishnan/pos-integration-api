import { ApiModelProperty } from "@nestjs/swagger/dist/decorators/api-model-property.decorator";

export class PosCredentialDto {
  @ApiModelProperty({ description: 'username' })
  public username: string;

  @ApiModelProperty({ description: 'password' })
  public password: string;

  @ApiModelProperty({ description: 'clientId' })
  public clientId: string;
}
