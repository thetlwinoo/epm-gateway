import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPhoneNumberType } from 'app/shared/model/phone-number-type.model';

type EntityResponseType = HttpResponse<IPhoneNumberType>;
type EntityArrayResponseType = HttpResponse<IPhoneNumberType[]>;

@Injectable({ providedIn: 'root' })
export class PhoneNumberTypeService {
  public resourceUrl = SERVER_API_URL + 'api/phone-number-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/phone-number-types';

  constructor(protected http: HttpClient) {}

  create(phoneNumberType: IPhoneNumberType): Observable<EntityResponseType> {
    return this.http.post<IPhoneNumberType>(this.resourceUrl, phoneNumberType, { observe: 'response' });
  }

  update(phoneNumberType: IPhoneNumberType): Observable<EntityResponseType> {
    return this.http.put<IPhoneNumberType>(this.resourceUrl, phoneNumberType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhoneNumberType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhoneNumberType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhoneNumberType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
