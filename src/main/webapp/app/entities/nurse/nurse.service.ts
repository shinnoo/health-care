import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { INurse } from 'app/shared/model/nurse.model';

type EntityResponseType = HttpResponse<INurse>;
type EntityArrayResponseType = HttpResponse<INurse[]>;

@Injectable({ providedIn: 'root' })
export class NurseService {
  public resourceUrl = SERVER_API_URL + 'api/nurses';

  constructor(protected http: HttpClient) {}

  create(nurse: INurse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nurse);
    return this.http
      .post<INurse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(nurse: INurse): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(nurse);
    return this.http
      .put<INurse>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INurse>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INurse[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(nurse: INurse): INurse {
    const copy: INurse = Object.assign({}, nurse, {
      createdDate: nurse.createdDate && nurse.createdDate.isValid() ? nurse.createdDate.toJSON() : undefined,
      lastModifiedDate: nurse.lastModifiedDate && nurse.lastModifiedDate.isValid() ? nurse.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((nurse: INurse) => {
        nurse.createdDate = nurse.createdDate ? moment(nurse.createdDate) : undefined;
        nurse.lastModifiedDate = nurse.lastModifiedDate ? moment(nurse.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
