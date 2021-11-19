import { classToPlain, plainToClass, ClassConstructor } from 'class-transformer';
import { Injectable } from "@nestjs/common";

@Injectable()
export class MapperService {

  public mapToDto<T, V>(plain: V, cls: ClassConstructor<T>): T {
    return plainToClass(cls, plain);
  }

  public mapToDomain<T, V>(cls: T) {
    return classToPlain(cls) as V;
  }
}
