import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMedicine } from 'app/shared/model/medicine.model';

type EntityResponseType = HttpResponse<IMedicine>;
type EntityArrayResponseType = HttpResponse<IMedicine[]>;

@Injectable({ providedIn: 'root' })
export class MedicineService {
  public resourceUrl = SERVER_API_URL + 'api/medicines';

  constructor(protected http: HttpClient) {}

  create(medicine: IMedicine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicine);
    return this.http
      .post<IMedicine>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicine: IMedicine): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicine);
    return this.http
      .put<IMedicine>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicine>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicine[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medicine: IMedicine): IMedicine {
    const copy: IMedicine = Object.assign({}, medicine, {
      createdDate: medicine.createdDate && medicine.createdDate.isValid() ? medicine.createdDate.toJSON() : undefined,
      lastModifiedDate: medicine.lastModifiedDate && medicine.lastModifiedDate.isValid() ? medicine.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((medicine: IMedicine) => {
        medicine.createdDate = medicine.createdDate ? moment(medicine.createdDate) : undefined;
        medicine.lastModifiedDate = medicine.lastModifiedDate ? moment(medicine.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
