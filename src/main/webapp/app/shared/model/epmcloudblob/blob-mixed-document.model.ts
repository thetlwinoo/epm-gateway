import { Moment } from 'moment';

export interface IBlobMixedDocument {
  id?: number;
  document?: any;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
}

export class BlobMixedDocument implements IBlobMixedDocument {
  constructor(public id?: number, public document?: any, public lastEditedBy?: string, public lastEditedWhen?: Moment) {}
}
