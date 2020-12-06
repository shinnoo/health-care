import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDoctor } from 'app/shared/model/doctor.model';

type EntityResponseType = HttpResponse<IDoctor>;
type EntityArrayResponseType = HttpResponse<IDoctor[]>;

@Injectable({ providedIn: 'root' })
export class DoctorService {
  public resourceUrl = SERVER_API_URL + 'api/doctors';

  constructor(protected http: HttpClient) {}

  create(doctor: IDoctor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctor);
    return this.http
      .post<IDoctor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(doctor: IDoctor): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(doctor);
    return this.http
      .put<IDoctor>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDoctor>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDoctor[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(doctor: IDoctor): IDoctor {
    const copy: IDoctor = Object.assign({}, doctor, {
      createdDate: doctor.createdDate && doctor.createdDate.isValid() ? doctor.createdDate.toJSON() : undefined,
      lastModifiedDate: doctor.lastModifiedDate && doctor.lastModifiedDate.isValid() ? doctor.lastModifiedDate.toJSON() : undefined,
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
      res.body.forEach((doctor: IDoctor) => {
        doctor.createdDate = doctor.createdDate ? moment(doctor.createdDate) : undefined;
        doctor.lastModifiedDate = doctor.lastModifiedDate ? moment(doctor.lastModifiedDate) : undefined;
      });
    }
    return res;
  }
}
