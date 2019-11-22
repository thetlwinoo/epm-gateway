import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBlobPhotos } from 'app/shared/model/epmcloudblob/blob-photos.model';

type EntityResponseType = HttpResponse<IBlobPhotos>;
type EntityArrayResponseType = HttpResponse<IBlobPhotos[]>;

@Injectable({ providedIn: 'root' })
export class BlobPhotosService {
  public resourceUrl = SERVER_API_URL + 'services/epmcloudblob/api/blob-photos';
  public resourceSearchUrl = SERVER_API_URL + 'services/epmcloudblob/api/_search/blob-photos';

  constructor(protected http: HttpClient) {}

  create(blobPhotos: IBlobPhotos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blobPhotos);
    return this.http
      .post<IBlobPhotos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(blobPhotos: IBlobPhotos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(blobPhotos);
    return this.http
      .put<IBlobPhotos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBlobPhotos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlobPhotos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBlobPhotos[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(blobPhotos: IBlobPhotos): IBlobPhotos {
    const copy: IBlobPhotos = Object.assign({}, blobPhotos, {
      lastEditedWhen: blobPhotos.lastEditedWhen != null && blobPhotos.lastEditedWhen.isValid() ? blobPhotos.lastEditedWhen.toJSON() : null
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
      res.body.forEach((blobPhotos: IBlobPhotos) => {
        blobPhotos.lastEditedWhen = blobPhotos.lastEditedWhen != null ? moment(blobPhotos.lastEditedWhen) : null;
      });
    }
    return res;
  }
}
