import { Module } from "@nestjs/common";
import { PosIntegrationController } from "./controllers/pos-integration.controller";
import { PaypalPosService } from "./services/adyen/paypal/paypal-pos.service";
import { SofortPosService } from "./services/adyen/sofort/sofort-pos.service";
import { EstPosService } from "./services/est/est-pos.service";
import { TyBankPosService } from "./services/ty-bank/ty-bank-pos.service";
import { MapperService } from "./services/mapper.service";

@Module({
  imports: [],
  controllers: [PosIntegrationController],
  providers: [TyBankPosService, EstPosService, SofortPosService, PaypalPosService, MapperService]
})
export class PostIntegrationModule {
}
