import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDisease } from 'app/shared/model/disease.model';

type EntityResponseType = HttpResponse<IDisease>;
type EntityArrayResponseType = HttpResponse<IDisease[]>;

@Injectable({ providedIn: 'root' })
export class DiseaseService {
  public resourceUrl = SERVER_API_URL + 'api/diseases';

  constructor(protected http: HttpClient) {}

  create(disease: IDisease): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disease);
    return this.http
      .post<IDisease>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(disease: IDisease): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(disease);
    return this.http
      .put<IDisease>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDisease>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDisease[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(disease: IDisease): IDisease {
    const copy: IDisease = Object.assign({}, disease, {
      createdDate: disease.createdDate && disease.createdDate.isValid() ? disease.createdDate.toJSON() : undefined,
      lastModifiedDate: disease.lastModifiedDate && disease.lastModifiedDate.isValid() ? disease.lastModifiedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((disease: IDisease) => {
        disease.createdDate = disease.createdDate ? moment(disease.createdDate) : undefined;
        disease.lastModifiedDate = disease.lastModifiedDate ? moment(disease.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
