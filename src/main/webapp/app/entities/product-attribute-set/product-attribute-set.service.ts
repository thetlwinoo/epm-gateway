import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductAttributeSet } from 'app/shared/model/product-attribute-set.model';

type EntityResponseType = HttpResponse<IProductAttributeSet>;
type EntityArrayResponseType = HttpResponse<IProductAttributeSet[]>;

@Injectable({ providedIn: 'root' })
export class ProductAttributeSetService {
  public resourceUrl = SERVER_API_URL + 'api/product-attribute-sets';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/product-attribute-sets';

  constructor(protected http: HttpClient) {}

  create(productAttributeSet: IProductAttributeSet): Observable<EntityResponseType> {
    return this.http.post<IProductAttributeSet>(this.resourceUrl, productAttributeSet, { observe: 'response' });
  }

  update(productAttributeSet: IProductAttributeSet): Observable<EntityResponseType> {
    return this.http.put<IProductAttributeSet>(this.resourceUrl, productAttributeSet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductAttributeSet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductAttributeSet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductAttributeSet[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
