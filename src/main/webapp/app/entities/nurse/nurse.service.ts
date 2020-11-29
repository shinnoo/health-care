import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<INurse>(this.resourceUrl, nurse, { observe: 'response' });
  }

  update(nurse: INurse): Observable<EntityResponseType> {
    return this.http.put<INurse>(this.resourceUrl, nurse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INurse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INurse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
