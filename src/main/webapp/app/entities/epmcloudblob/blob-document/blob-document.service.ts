import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBlobDocument } from 'app/shared/model/epmcloudblob/blob-document.model';

type EntityResponseType = HttpResponse<IBlobDocument>;
type EntityArrayResponseType = HttpResponse<IBlobDocument[]>;

@Injectable({ providedIn: 'root' })
export class BlobDocumentService {
  public resourceUrl = SERVER_API_URL + 'services/epmcloudblob/api/blob-documents';
  public resourceSearchUrl = SERVER_API_URL + 'services/epmcloudblob/api/_search/blob-documents';

  constructor(protected http: HttpClient) {}

  create(blobDocument: IBlobDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blobDocument);
    return this.http
      .post<IBlobDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(blobDocument: IBlobDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blobDocument);
    return this.http
      .put<IBlobDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBlobDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlobDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlobDocument[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(blobDocument: IBlobDocument): IBlobDocument {
    const copy: IBlobDocument = Object.assign({}, blobDocument, {
      lastEditedWhen:
        blobDocument.lastEditedWhen != null && blobDocument.lastEditedWhen.isValid() ? blobDocument.lastEditedWhen.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.lastEditedWhen = res.body.lastEditedWhen != null ? moment(res.body.lastEditedWhen) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((blobDocument: IBlobDocument) => {
        blobDocument.lastEditedWhen = blobDocument.lastEditedWhen != null ? moment(blobDocument.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
