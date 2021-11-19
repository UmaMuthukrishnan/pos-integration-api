import { NestFactory } from "@nestjs/core";
import { PostIntegrationModule } from "./pos-integration/post-integration.module";
import { DocumentBuilder, SwaggerModule } from "@nestjs/swagger";

async function bootstrap() {
  const app = await NestFactory.create(PostIntegrationModule);

  const config = new DocumentBuilder()
    .setTitle('Trendyol Hiring Day Case | Node')
    .setDescription('Trendyol Hiring Day Case | Node')
    .setVersion('1.0')
    .build();
  const document = SwaggerModule.createDocument(app, config);

  SwaggerModule.setup('swagger', app, document);

  await app.listen(3000);
}

bootstrap();
