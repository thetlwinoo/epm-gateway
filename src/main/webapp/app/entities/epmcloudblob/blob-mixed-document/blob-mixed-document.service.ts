import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBlobMixedDocument } from 'app/shared/model/epmcloudblob/blob-mixed-document.model';

type EntityResponseType = HttpResponse<IBlobMixedDocument>;
type EntityArrayResponseType = HttpResponse<IBlobMixedDocument[]>;

@Injectable({ providedIn: 'root' })
export class BlobMixedDocumentService {
  public resourceUrl = SERVER_API_URL + 'services/epmcloudblob/api/blob-mixed-documents';
  public resourceSearchUrl = SERVER_API_URL + 'services/epmcloudblob/api/_search/blob-mixed-documents';

  constructor(protected http: HttpClient) {}

  create(blobMixedDocument: IBlobMixedDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blobMixedDocument);
    return this.http
      .post<IBlobMixedDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(blobMixedDocument: IBlobMixedDocument): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blobMixedDocument);
    return this.http
      .put<IBlobMixedDocument>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBlobMixedDocument>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlobMixedDocument[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlobMixedDocument[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(blobMixedDocument: IBlobMixedDocument): IBlobMixedDocument {
    const copy: IBlobMixedDocument = Object.assign({}, blobMixedDocument, {
      lastEditedWhen:
        blobMixedDocument.lastEditedWhen != null && blobMixedDocument.lastEditedWhen.isValid()
          ? blobMixedDocument.lastEditedWhen.toJSON()
          : null
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
      res.body.forEach((blobMixedDocument: IBlobMixedDocument) => {
        blobMixedDocument.lastEditedWhen = blobMixedDocument.lastEditedWhen != null ? moment(blobMixedDocument.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
