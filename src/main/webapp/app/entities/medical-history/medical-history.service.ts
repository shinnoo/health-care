import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicalHistory } from 'app/shared/model/medical-history.model';

type EntityResponseType = HttpResponse<IMedicalHistory>;
type EntityArrayResponseType = HttpResponse<IMedicalHistory[]>;

@Injectable({ providedIn: 'root' })
export class MedicalHistoryService {
  public resourceUrl = SERVER_API_URL + 'api/medical-histories';

  constructor(protected http: HttpClient) {}

  create(medicalHistory: IMedicalHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalHistory);
    return this.http
      .post<IMedicalHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicalHistory: IMedicalHistory): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicalHistory);
    return this.http
      .put<IMedicalHistory>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicalHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicalHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medicalHistory: IMedicalHistory): IMedicalHistory {
    const copy: IMedicalHistory = Object.assign({}, medicalHistory, {
      joinedAt: medicalHistory.joinedAt && medicalHistory.joinedAt.isValid() ? medicalHistory.joinedAt.toJSON() : undefined,
      leavedAt: medicalHistory.leavedAt && medicalHistory.leavedAt.isValid() ? medicalHistory.leavedAt.toJSON() : undefined,
      createdDate: medicalHistory.createdDate && medicalHistory.createdDate.isValid() ? medicalHistory.createdDate.toJSON() : undefined,
      lastModifiedDate:
        medicalHistory.lastModifiedDate && medicalHistory.lastModifiedDate.isValid() ? medicalHistory.lastModifiedDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.joinedAt = res.body.joinedAt ? moment(res.body.joinedAt) : undefined;
      res.body.leavedAt = res.body.leavedAt ? moment(res.body.leavedAt) : undefined;
      res.body.createdDate = res.body.createdDate ? moment(res.body.createdDate) : undefined;
      res.body.lastModifiedDate = res.body.lastModifiedDate ? moment(res.body.lastModifiedDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((medicalHistory: IMedicalHistory) => {
        medicalHistory.joinedAt = medicalHistory.joinedAt ? moment(medicalHistory.joinedAt) : undefined;
        medicalHistory.leavedAt = medicalHistory.leavedAt ? moment(medicalHistory.leavedAt) : undefined;
        medicalHistory.createdDate = medicalHistory.createdDate ? moment(medicalHistory.createdDate) : undefined;
        medicalHistory.lastModifiedDate = medicalHistory.lastModifiedDate ? moment(medicalHistory.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
