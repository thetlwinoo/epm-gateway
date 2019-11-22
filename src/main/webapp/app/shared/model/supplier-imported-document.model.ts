import { Moment } from 'moment';
import { DocumentType } from 'app/shared/model/enumerations/document-type.model';

export interface ISupplierImportedDocument {
  id?: number;
  documentUrl?: string;
  documentType?: DocumentType;
  lastEditedBy?: string;
  lastEditedWhen?: Moment;
  uploadTransactionId?: number;
}

export class SupplierImportedDocument implements ISupplierImportedDocument {
  constructor(
    public id?: number,
    public documentUrl?: string,
    public documentType?: DocumentType,
    public lastEditedBy?: string,
    public lastEditedWhen?: Moment,
    public uploadTransactionId?: number
  ) {}
}
