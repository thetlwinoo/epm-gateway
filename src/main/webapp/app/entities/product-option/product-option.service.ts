import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductOption } from 'app/shared/model/product-option.model';

type EntityResponseType = HttpResponse<IProductOption>;
type EntityArrayResponseType = HttpResponse<IProductOption[]>;

@Injectable({ providedIn: 'root' })
export class ProductOptionService {
  public resourceUrl = SERVER_API_URL + 'api/product-options';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/product-options';

  constructor(protected http: HttpClient) {}

  create(productOption: IProductOption): Observable<EntityResponseType> {
    return this.http.post<IProductOption>(this.resourceUrl, productOption, { observe: 'response' });
  }

  update(productOption: IProductOption): Observable<EntityResponseType> {
    return this.http.put<IProductOption>(this.resourceUrl, productOption, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductOption>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductOption[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductOption[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
