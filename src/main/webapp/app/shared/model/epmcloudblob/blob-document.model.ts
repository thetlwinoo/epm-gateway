import { Moment } from 'moment';

export interface IBlobDocument {
  id?: number;
  documentContentType?: string;
  document?: any;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
}

export class BlobDocument implements IBlobDocument {
  constructor(
    public id?: number,
    public documentContentType?: string,
    public document?: any,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment
  ) {}
}
