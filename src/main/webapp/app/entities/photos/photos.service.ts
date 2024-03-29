import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPhotos } from 'app/shared/model/photos.model';

type EntityResponseType = HttpResponse<IPhotos>;
type EntityArrayResponseType = HttpResponse<IPhotos[]>;

@Injectable({ providedIn: 'root' })
export class PhotosService {
  public resourceUrl = SERVER_API_URL + 'api/photos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/photos';

  constructor(protected http: HttpClient) {}

  create(photos: IPhotos): Observable<EntityResponseType> {
    return this.http.post<IPhotos>(this.resourceUrl, photos, { observe: 'response' });
  }

  update(photos: IPhotos): Observable<EntityResponseType> {
    return this.http.put<IPhotos>(this.resourceUrl, photos, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPhotos>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhotos[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPhotos[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
