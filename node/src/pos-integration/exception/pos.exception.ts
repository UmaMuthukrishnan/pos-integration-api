export class PosException extends Error {
  private readonly innerException: Error;

  public constructor(message?: string, error?: Error) {
    super(message);
    this.innerException = error;
  }
}
