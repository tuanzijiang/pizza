import { ResponseSchema } from './schema';

export class Response implements ResponseSchema {
  public url: string = null;
  public method: string = null;

  constructor(props: ResponseSchema) {
    this.url = props.url;
    this.method = props.method;
  }
}
