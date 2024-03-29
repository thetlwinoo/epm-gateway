import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBarcodeTypes } from 'app/shared/model/barcode-types.model';

type EntityResponseType = HttpResponse<IBarcodeTypes>;
type EntityArrayResponseType = HttpResponse<IBarcodeTypes[]>;

@Injectable({ providedIn: 'root' })
export class BarcodeTypesService {
  public resourceUrl = SERVER_API_URL + 'api/barcode-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/barcode-types';

  constructor(protected http: HttpClient) {}

  create(barcodeTypes: IBarcodeTypes): Observable<EntityResponseType> {
    return this.http.post<IBarcodeTypes>(this.resourceUrl, barcodeTypes, { observe: 'response' });
  }

  update(barcodeTypes: IBarcodeTypes): Observable<EntityResponseType> {
    return this.http.put<IBarcodeTypes>(this.resourceUrl, barcodeTypes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBarcodeTypes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBarcodeTypes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBarcodeTypes[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
